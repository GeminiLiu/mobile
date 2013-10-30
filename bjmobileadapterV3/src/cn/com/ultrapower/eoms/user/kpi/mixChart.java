package cn.com.ultrapower.eoms.user.kpi;

import java.awt.Color;
import java.awt.GradientPaint;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.StandardXYZToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.urls.TimeSeriesURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

/**
 * 日期 2007-4-20
 * @author xuquanxing
 */
public class mixChart
{

	public mixChart(String arg0)
	{
		//super(arg0);
	}

	/**
	 * 
	 */
	 private static final long serialVersionUID = 1L;

      /**
     * 日期 2007-4-23
     * @author xuquanxing 
     * @param barcategorydataset   barchart dataset
     * @param linecategorydataset  linechart dataset
     * @param kpititile            charttitle
     * @param entitytitile         bottomtitle
     * @param leftaxisname         leftaxis
     * @param rightaxisname        rightaxis
     * @return JFreeChart
     */
    public JFreeChart createChooseChart(CategoryDataset barcategorydataset,CategoryDataset linecategorydataset,
              String kpititile,String entitytitile,String leftaxisname,String  rightaxisname)
      {
    	System.out.println("正在创建图片开始。。。 ");  
    	JFreeChart  chart = null;
    	  if(barcategorydataset==null)//create line chart
    	  {
    	  }else if(linecategorydataset==null)//create bar chart no implemention
    	  {
    		  chart = createBarChart(barcategorydataset,kpititile,entitytitile,leftaxisname);
    	  }else//create double axis chart
    	  {
    		  chart= createChart(barcategorydataset,linecategorydataset,
                       kpititile,entitytitile,leftaxisname,rightaxisname);
    	  }
      	 System.out.println("正在创建图片结束。。。");  
    	  return chart;
      }
      
      private JFreeChart createBarChart(CategoryDataset dataset,String kpititile,String entitytitile,String leftaxisname)
      {
  		JFreeChart chart = ChartFactory.createBarChart3D(kpititile, // 图表标题
  				entitytitile, // 目录轴的显示标签
  				leftaxisname, // 数值轴的显示标签
				dataset, // 数据集
				PlotOrientation.VERTICAL, // 图表方向：水平、垂直
				true, // 是否显示图例(对于简单的柱状图必须是false) 底部的label是否显示
				true, // 是否生成工具
				false // 是否生成URL链接
				);
        GradientPaint bgGP = new GradientPaint(0, 1000,  Color.BLUE, 0, 0,
        	    Color.CYAN, false);
        chart.setBackgroundPaint(bgGP);
        CategoryPlot   	plot   				=   chart.getCategoryPlot(); 
        CategoryAxis	categoryaxis		= plot.getDomainAxis();
        categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        categoryaxis.setMaximumCategoryLabelWidthRatio(20F);
      //  BarRenderer renderer  =   new BarRenderer();    
      //  renderer.setToolTipGenerator(new  StandardCategoryToolTipGenerator());
      //  plot.setRenderer(renderer);
  		return chart;
      }
      
	/**
	 * 日期 2007-4-23
	 * @author xuquanxing 
	 * @param barcategorydataset     柱状数据集
	 * @param linecategorydataset    折线数据集
	 * @return JFreeChart
	 */
	private  JFreeChart createChart(CategoryDataset barcategorydataset,CategoryDataset linecategorydataset,
			                       String kpititile,String entitytitile,String leftaxisname,String  rightaxisname )
	{
    
        JFreeChart jfreechart = ChartFactory.createBarChart3D(
        		                                              kpititile,
        		                                              entitytitile, 
        	                                                  leftaxisname, 
        	                                                  barcategorydataset,
        	                                                  PlotOrientation.VERTICAL, 
        	                                                  true,
        	                                                  true, 
        	                                                  false
        	                                                  );
       
        //设置图片背景色
        GradientPaint bgGP = new GradientPaint(0, 1000, Color.MAGENTA, 0, 0,
        	    Color.WHITE, false);
        jfreechart.setBackgroundPaint(bgGP);
        //***********************增加热点提示********************************
        CategoryPlot categoryplot3 = (CategoryPlot) jfreechart.getPlot();
        BarRenderer  render = (BarRenderer)categoryplot3.getRenderer();
        render.setToolTipGenerator(new StandardCategoryToolTipGenerator());
      //  StandardXYItemRenderer ren = (StandardXYItemRenderer)categoryplot3.getRenderer();
      // ren.setToolTipGenerator(new StandardXYZToolTipGenerator());
        //*****************************增加热点提示结束*****************************
      
        // StandardCategoryToolTipGenerator stand =  new StandardCategoryToolTipGenerator();
       // stand.generateToolTip(barcategorydataset,0,2);
       // stand.generateToolTip(linecategorydataset,1,2);
      //  categoryplot3.setToolTipGenerator(new StandardPieToolTipGenerator());
  //      NumberAxis numberaxisbar = (NumberAxis) categoryplot3.getRangeAxis();
//      设置最高的一个 Item 与图片顶端的距离
 //       numberaxisbar.setUpperBound(0.15);
 //       numberaxisbar.setLowerMargin(1000.00);
  //      categoryplot3.setRangeAxis(numberaxisbar);
        //System.out.println("categoryplot3.getRangeAxis()"+categoryplot3.getRangeAxis(0).getRange().getLength());
       
        CategoryAxis categoryaxis = categoryplot3.getDomainAxis();
       // CategoryLabelPositions.DOWN_45 表示label样式 倾斜角度
        categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        //设置标签宽度
        categoryaxis.setMaximumCategoryLabelWidthRatio(20F);
        
        NumberAxis numberaxis3 = new NumberAxis(rightaxisname);
        categoryplot3.setRangeAxis(1, numberaxis3);
        categoryplot3.setDataset(1, linecategorydataset);//设置数据集索引
        categoryplot3.mapDatasetToRangeAxis(1,1);//将该索引映射到axis 第一个参数指数据集的索引,第二个参数为坐标轴的索引
        LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
       
        lineandshaperenderer.setToolTipGenerator(new StandardCategoryToolTipGenerator());
        lineandshaperenderer.setShapesVisible(true);
        lineandshaperenderer.setItemLabelAnchorOffset(1D);
        //设置某坐标轴索引上数据集的显示样式
         categoryplot3.setRenderer(1, lineandshaperenderer);
        //设置两个图的前后顺序 ，DatasetRenderingOrder.FORWARD表示后面的图在前者上面 ，DatasetRenderingOrder.REVERSE表示 表示后面的图在前者后面
        categoryplot3.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
       
       // LineAndShapeRenderer  ren = (LineAndShapeRenderer)categoryplot3.getRenderer();
       // ren.setToolTipGenerator(new StandardCategoryToolTipGenerator("{0}-{1}:2}",NumberFormat.getInstance()));
        return jfreechart;
	}

	
	/**
	 * 日期 2007-4-23
	 * @author xuquanxing 
	 * @param indexrelative     map包含: 关键字=指标集名,value=map对象,其包含关键字日期,值是指标值
	 * @return CategoryDataset 
	 *  折线的数据集,
	 */
	public  CategoryDataset createDataset(Map indexrelative)
	    {
		   System.out.println("正在创建数据集合 ");
		   DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
	        Iterator indexset = indexrelative.keySet().iterator();
	        while(indexset.hasNext())
	        {
	        	String  indexrel   = (String)indexset.next();//取得集合的关键字,即具体指标名称
	        	LinkedHashMap  indexval  = (LinkedHashMap)indexrelative.get(indexrel);//取得该关键字对应的value
	        	Iterator indexinfo = indexval.keySet().iterator();
	        	while(indexinfo.hasNext())
	        	{
	        		String  indexkey    = (String)indexinfo.next();//取得集合对应日期
	        		String  indexvalue  = (String)indexval.get(indexkey);//取得集合的关键字,即指标数据值
	    	        defaultcategorydataset.addValue(Double.parseDouble(indexvalue), indexrel,indexkey);
	        	}
         }
			   System.out.println("创建数据集合结束  ");
	        return defaultcategorydataset;
	    }
}
