package cn.itheima.index;

import cn.itheima.dao.BookDao;
import cn.itheima.dao.impl.BookDaoImpl;
import cn.itheima.po.Book;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IndexManager {

    @Test
    public void createIndex() throws IOException {

        BookDao bookDao = new BookDaoImpl();

        List<Book> bookList = bookDao.findBookList();

        List<Document> docList = new ArrayList<Document>();
        for (Book book : bookList) {
            Document doc = new Document();

            doc.add(new TextField("bookId", book.getId() + "", Field.Store.YES));
            doc.add(new TextField("bookName", book.getBookname() + "", Field.Store.YES));
            doc.add(new TextField("bookPrice", book.getPrice() + "", Field.Store.YES));
            doc.add(new TextField("bookPic", book.getPic() + "", Field.Store.YES));
            doc.add(new TextField("bookDesc", book.getBookdesc() + "", Field.Store.YES));

            docList.add(doc);
        }

        StandardAnalyzer standardAnalyzer = new StandardAnalyzer();

        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_3,standardAnalyzer);

        File file = new File("D:/JAVA就业班/就业班第一阶段/day94_lucene&solr_day_01/");
        Directory open = FSDirectory.open(file);

        IndexWriter indexManager = new IndexWriter(open, indexWriterConfig);

        for(Document document : docList){
            indexManager.addDocument(document);
        }

        indexManager.close();

    }

    @Test
    public void readIndex() throws Exception {

        Analyzer standardAnalyzer = new IKAnalyzer();

        QueryParser bookName = new QueryParser("bookName", standardAnalyzer);

        Query parse = bookName.parse("bookName:java");

        FSDirectory open = FSDirectory.open(new File("D:/JAVA就业班/就业班第一阶段/day94_lucene&solr_day_01/"));

        IndexReader reader = DirectoryReader.open(open);

        IndexSearcher searcher = new IndexSearcher(reader);

        TopDocs topDoc  = searcher.search(parse, 10);

        System.out.println("实际查询数量:"+topDoc .totalHits);

        ScoreDoc[] scoreDocs = topDoc.scoreDocs;

        for(ScoreDoc docs : scoreDocs){
            int docId = docs.doc;
            float score = docs.score;

            System.out.println("文档的Id："+docId+",文档分值："+score);

            Document document = searcher.doc(docId);

            System.out.println("图书Id："+document.get("bookId"));
            System.out.println("图书名称："+document.get("bookName"));
            System.out.println("图书价格："+document.get("bookPrice"));
            System.out.println("图书图片："+document.get("bookPic"));
            System.out.println("图书描述："+document.get("bookDesc"));


        }

        reader.close();

    }
}
