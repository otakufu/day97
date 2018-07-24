package cn.itheima.dao;

import cn.itheima.po.Book;

import java.util.List;

public interface BookDao {

    List<Book> findBookList();

}
