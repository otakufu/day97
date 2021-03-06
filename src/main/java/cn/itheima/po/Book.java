package cn.itheima.po;

public class Book {

    private Integer id;
    private String bookname;
    private Float price;
    private String pic;
//    123
//    222
    private String bookdesc;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookname + '\'' +
                ", price=" + price +
                ", pic='" + pic + '\'' +
                ", bookdesc='" + bookdesc + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getBookdesc() {
        return bookdesc;
    }

    public void setBookdesc(String bookdesc) {
        this.bookdesc = bookdesc;
    }
}
