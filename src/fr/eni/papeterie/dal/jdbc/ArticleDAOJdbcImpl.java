/**
 * 
 */
package fr.eni.papeterie.dal.jdbc;


import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Ramette;
import fr.eni.papeterie.bo.Stylo;
import fr.eni.papeterie.dal.DALException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Eni Ecole
 * 
 */
public class ArticleDAOJdbcImpl {
    private static final String SQL_INSERT_ARTICLE = "INSERT INTO Articles"+
                                                     " (reference,"+
                                                     " marque,"+
                                                     " designation,"+
                                                     " prixUnitaire,"+
                                                     " qteStock,"+
                                                     " grammage,"+
                                                     " couleur,"+
                                                     " type)"+
                                                     " VALUES (?,?,?,?,?,?,?,?)";

    private static final String SQL_SELECT_BY_ID ="SELECT * FROM Articles"+
                                                  " WHERE idArticle = ?;";

    private static final String SQL_SELECT_ALL ="SELECT * FROM Articles";

    private static String SQL_DELETE = "DELETE FROM Articles"+
                                       " WHERE idArticle = ?";

    private static String SQL_UPDATE = "UPDATE Articles " +
                                       "SET reference = ?, " +
                                       "marque = ?, "+
                                       "designation = ?, "+
                                       "prixUnitaire = ?, "+
                                       "qteStock= ?, "+
                                       "grammage= ?, "+
                                       "couleur= ?, "+
                                       "type =? "+
                                       "WHERE idArticle= ?;";

    public void insert(Article a1) throws DALException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
             connection= BddJdbc.getConnexion();
             preparedStatement = connection.prepareStatement(SQL_INSERT_ARTICLE,Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, a1.getReference());
            preparedStatement.setString(2,a1.getMarque());
            preparedStatement.setString(3,a1.getDesignation());
            preparedStatement.setFloat(4,a1.getPrixUnitaire());
            preparedStatement.setInt(5,a1.getQteStock());

            if (a1 instanceof Stylo){
                preparedStatement.setInt(6,0);
                preparedStatement.setString(7, ((Stylo) a1).getCouleur());
                preparedStatement.setString(8,a1.getClass().getSimpleName());
            }
            if(a1 instanceof Ramette){
                preparedStatement.setInt(6,((Ramette) a1).getGrammage());
                preparedStatement.setString(7,null);
                preparedStatement.setString(8,a1.getClass().getSimpleName());
            }
            preparedStatement.executeUpdate();
            ResultSet rst = preparedStatement.getGeneratedKeys();
            rst.next();
            a1.setIdArticle(rst.getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DALException("Erreur pendant l'insertion",e);
        }
        finally {
            try {
                preparedStatement.close();
                BddJdbc.closeConnexion(connection);
            } catch (SQLException e) {
                throw new DALException("Erreur de deconnection",e);
            }
        }

    }


    public Article selectById(Integer idArticle) throws DALException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Article article = null;
        try {
            connection= BddJdbc.getConnexion();
            preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1,idArticle);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                article=resultSetToArticle(result);
            }

            return article;


        } catch (SQLException e) {
            e.printStackTrace();
            throw new DALException("Erreur la requête",e);
        }
        finally {
            try {
                preparedStatement.close();
                BddJdbc.closeConnexion(connection);
            } catch (SQLException e) {
                throw new DALException("Erreur de deconnection",e);
            }
        }
    }
private Article resultSetToArticle(ResultSet resultSet) throws SQLException {
    Article article = null ;
    if("Stylo".equals(resultSet.getString("type").trim())){
        article = new Stylo();
        ((Stylo) article).setCouleur(resultSet.getString("couleur"));
    }else {
        article = new Ramette();
        ((Ramette) article).setGrammage(resultSet.getInt("grammage"));
    }
    article.setIdArticle(resultSet.getInt("idArticle"));
    article.setReference(resultSet.getString("reference"));
    article.setMarque(resultSet.getString("marque"));
    article.setDesignation(resultSet.getString("designation"));
    article.setPrixUnitaire(resultSet.getFloat("prixUnitaire"));
    article.setQteStock(resultSet.getInt("qteStock"));

    return  article;
}

    public List<Article> selectAll() throws DALException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Article article = null ;
        ArrayList<Article> articles = new ArrayList<>();
        try {
            connection= BddJdbc.getConnexion();
            preparedStatement = connection.prepareStatement(SQL_SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                article = resultSetToArticle(resultSet);
                articles.add(article);
            }
            return articles;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DALException("Erreur la requête",e);
        }
        finally {
            try {
                preparedStatement.close();
                BddJdbc.closeConnexion(connection);
            } catch (SQLException e) {
                throw new DALException("Erreur de deconnection",e);
            }
        }

    }

    public void update(Article a1) throws DALException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection= BddJdbc.getConnexion();
            preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setInt(9,a1.getIdArticle());

            preparedStatement.setString(1, a1.getReference());
            preparedStatement.setString(2,a1.getMarque());
            preparedStatement.setString(3,a1.getDesignation());
            preparedStatement.setFloat(4,a1.getPrixUnitaire());
            preparedStatement.setInt(5,a1.getQteStock());

            if (a1 instanceof Stylo){
                preparedStatement.setInt(6,0);
                preparedStatement.setString(7, ((Stylo) a1).getCouleur());
                preparedStatement.setString(8,a1.getClass().getSimpleName());
            }
            if (a1 instanceof Ramette){
                preparedStatement.setInt(6,((Ramette) a1).getGrammage());
                preparedStatement.setString(7,null);
                preparedStatement.setString(8,a1.getClass().getSimpleName());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DALException("Erreur la requête",e);
        }
        finally {
            try {
                preparedStatement.close();
                BddJdbc.closeConnexion(connection);
            } catch (SQLException e) {
                throw new DALException("Erreur de deconnection",e);
            }
        }



    }


    public void delete(Integer idArticle) throws DALException  {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection= BddJdbc.getConnexion();
            preparedStatement = connection.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1,idArticle);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DALException("Erreur la requête",e);
        }
        finally {
            try {
                preparedStatement.close();
                BddJdbc.closeConnexion(connection);
            } catch (SQLException e) {
                throw new DALException("Erreur de deconnection",e);
            }
        }
    }


}
