package com.hss.lucene;

import com.hss.entity.Blog;
import com.hss.util.DateUtil;
import com.hss.util.StringUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.util.StringUtils;

import java.io.StringReader;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * lucene索引类
 */
public class BlogIndex
{
    private Directory dir;

    /**
     * 获取index Writer
     * @return
     * @throws Exception
     */
    private IndexWriter getWriter() throws  Exception
    {
        dir= FSDirectory.open(Paths.get("C:/Users/hu/Desktop/lucene"));
        SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
        IndexWriterConfig indexWriterConfig=new IndexWriterConfig(analyzer);
        IndexWriter writer=new IndexWriter(dir,indexWriterConfig);
        return writer;
    }
    //添加索引
    public void addIndex(Blog blog) throws  Exception
    {
        //stringField是不会用到分词的
        IndexWriter writer=getWriter();
        Document document=new Document();
        document.add(new StringField("id",String.valueOf(blog.getId()), Field.Store.YES));
        document.add(new TextField("title",blog.getTitle(),Field.Store.YES));
        document.add(new StringField("releaseDate", DateUtil.formatDate(new Date(), "yyyy-MM-dd"),Field.Store.YES));
        document.add(new TextField("content",blog.getContentNoTag(),Field.Store.YES));
        writer.addDocument(document);
        writer.close();
    }
    //删除索引
    public void deleteIndex(String blogId)throws Exception{
        IndexWriter writer=getWriter();
        writer.deleteDocuments(new Term("id",blogId));
        writer.forceMergeDeletes(); // 强制删除
        writer.commit();
        writer.close();
    }
    //修改索引
    public void updateIndex(Blog blog)throws Exception{
        IndexWriter writer=getWriter();
        Document doc=new Document();
        doc.add(new StringField("id",String.valueOf(blog.getId()),Field.Store.YES));
        doc.add(new TextField("title",blog.getTitle(),Field.Store.YES));
        doc.add(new StringField("releaseDate",DateUtil.formatDate(new Date(), "yyyy-MM-dd"),Field.Store.YES));
        doc.add(new TextField("content",blog.getContentNoTag(),Field.Store.YES));
        writer.updateDocument(new Term("id",String.valueOf(blog.getId())), doc);
        writer.close();
    }
    //查询
    public List<Blog> searchBlog(String q) throws  Exception
    {
        dir=FSDirectory.open(Paths.get("C:/Users/hu/Desktop/lucene"));
        IndexReader reader= DirectoryReader.open(dir);
        IndexSearcher indexSearcher=new IndexSearcher(reader);
        //多条件查询BooleanQuery 封装查询
        BooleanQuery.Builder builder=new BooleanQuery.Builder();
        SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();

        QueryParser parser=new QueryParser("title",analyzer);
        Query query=parser.parse(q);

        QueryParser parser2=new QueryParser("content",analyzer);
        Query query2=parser2.parse(q);

        //表示查询条件是或的关系 有的即会返回
        builder.add(query, BooleanClause.Occur.SHOULD);
        builder.add(query2, BooleanClause.Occur.SHOULD);

        //查询钱100条数据
        TopDocs hits=indexSearcher.search(builder.build(),100);
        QueryScorer scorer=new QueryScorer(query);//标题得分高的放在前面
        //创建代码高亮
        Fragmenter fragmenter=new SimpleSpanFragmenter(scorer);
        SimpleHTMLFormatter simpleHTMLFormatter=new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");
        Highlighter highlighter=new Highlighter(simpleHTMLFormatter,scorer);
        highlighter.setTextFragmenter(fragmenter);

        List<Blog> blogList=new LinkedList<>();
        for(ScoreDoc scoreDoc:hits.scoreDocs)
        {
            Document document=indexSearcher.doc(scoreDoc.doc);
            Blog blog=new Blog();
            blog.setId(Integer.parseInt(document.get("id")));
            blog.setReleaseDateStr(document.get("releaseDate"));

            String title= document.get("title");
            //对文本中的标签进行过滤转义
            String content= StringEscapeUtils.escapeHtml(document.get("content"));
            if(title!=null)
            {
                TokenStream tokenStream=analyzer.tokenStream("title",new StringReader(title));
                String htitle=highlighter.getBestFragment(tokenStream,title);
                if(StringUtil.isNotEmpty(htitle))
                {
                    blog.setTitle(htitle);
                }
                else blog.setTitle(title);
            }
            if(content!=null)
            {
                TokenStream tokenStream=analyzer.tokenStream("content",new StringReader(content));
                String hcontent=highlighter.getBestFragment(tokenStream,content);
                if(StringUtil.isEmpty(hcontent))
                {
                    if(content.length()<=200)
                    {
                        blog.setContent(content);
                    }else {
                        blog.setContent(content.substring(0,200));
                    }

                }
                else blog.setContent(hcontent);
            }
            blogList.add(blog);

        }

        return blogList;
    }
}
