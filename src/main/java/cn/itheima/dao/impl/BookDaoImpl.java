package cn.itheima.dao.impl;

import cn.itheima.dao.BookDao;
import cn.itheima.po.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {


    public List<Book> findBookList() {

        List<Book> bookList = new ArrayList<Book>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection =
                    DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/day94","root","root");

            preparedStatement = connection.prepareStatement("select * from book");

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Book book = new Book();

                book.setId(resultSet.getInt("id"));
                book.setBookname(resultSet.getString("bookname"));
                book.setPrice(resultSet.getFloat("price"));
                book.setPic(resultSet.getString("pic"));
                book.setBookdesc(resultSet.getString("bookdesc"));

                bookList.add(book);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {

            try {
                if(resultSet != null){
                    resultSet.close();
                }

                if(preparedStatement != null){
                    preparedStatement.close();
                }

                if(connection != null){
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        return bookList;
    }
}
