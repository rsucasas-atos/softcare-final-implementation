package eu.ehealth.dataprocess.measurements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.encoders.EncoderUtil;
import org.jfree.chart.encoders.ImageFormat;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.zkoss.image.AImage;
import eu.ehealth.SystemDictionary;
import eu.ehealth.ws_client.xsd.Measurement;


/**
 * 
 * @author a572832
 *
 */
public class MeasurementCharts
{
	
	
	private int _width = 1200;
	private int _height = 600;
	
	
	/**
	 * 
	 */
	public MeasurementCharts() { }
	
	
	/**
	 * 
	 * @param width
	 * @param height
	 */
	public MeasurementCharts(int width, int height) {
		_width = width;
		_height = height;
	}
	
	
	/**
	 * 
	 * @param mtype
	 * @param measurementlist
	 * @return
	 */
	public AImage generateChart(String mtype, List<Measurement>[] measurementlist)
	{
		try
		{
			SystemDictionary.webguiLog("DEBUG", "measurementlist.length " + measurementlist.length);
			// WEIGHT
			if (mtype.equals(SystemDictionary.TASK_TYPE_WEIGHT_MEASUREMENT))
			{
				return createChart(createOneSerieMeasurementsDataset(measurementlist[0], "Weight Measurement"), 
								   "Weight Measurements", 
								   "Weight [Kg.]",
								   SystemDictionary.PROPERTIES_HASHMAP.get(SystemDictionary.PROPERTY_WEIGHT_MIN),
								   SystemDictionary.PROPERTIES_HASHMAP.get(SystemDictionary.PROPERTY_WEIGHT_MAX));
			}
			// ACTIVITY - NUMBER OF STEPS
			else if (mtype.equals(SystemDictionary.TASK_TYPE_ACTMONITOR))
			{
				return createOverlaidCharts(createSerieMeasurementDatasetWithFlatSeries(measurementlist[0], "Activity"), 
										    "Activity", 
										    "steps / day",
										    "0",
										    "15000");
			}
			// BLOOD PRESSURE
			else if (mtype.equals(SystemDictionary.TASK_TYPE_BLOODPRESSURE_MEASUREMENT))
			{
				SystemDictionary.webguiLog("DEBUG", "createTwoSeriesMeasurementsDataset... ");
				return createChart(createTwoSeriesMeasurementsDataset(measurementlist, "Systolic", "Diastolic"),
								   "Bood Pressure",
								   "Value [mmHg]",
								   SystemDictionary.PROPERTIES_HASHMAP.get(SystemDictionary.PROPERTY_BLOOD_DIASTOLIC_MIN),
								   SystemDictionary.PROPERTIES_HASHMAP.get(SystemDictionary.PROPERTY_BLOOD_SISTOLIC_MAX));
			}

			return null;
		}
		catch (Exception e)
		{
			SystemDictionary.webguiLog("ERROR", e.getMessage());
			e.printStackTrace();
			
			return null;
		}
	}
	
	
	/**
	 * 
	 * @param xydataset
	 * @param title
	 * @param field1
	 * @param minValue
	 * @param maxValue
	 * @return
	 */
	private AImage createChart(XYDataset xydataset, String title, String field1, String minValue, String maxValue)
    {
		try
		{
	        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(
	        		title, 
	        		"Day [and hour]", 
	        		field1, 
	        		xydataset, 
	        		true, 
	        		true, 
	        		false);
	        
	        XYPlot xyplot = (XYPlot)jfreechart.getPlot();
	        
	        DateAxis dateaxis = (DateAxis)xyplot.getDomainAxis();
	        dateaxis.setDateFormatOverride(new SimpleDateFormat("dd-MMM-yyyy"));
	        
	        ValueAxis valueaxis = xyplot.getRangeAxis();
	        valueaxis.setAutoRangeMinimumSize(1.0D);
	        valueaxis.setLowerBound(Double.parseDouble(minValue));
	        valueaxis.setUpperBound(Double.parseDouble(maxValue));
	        
	        // line thickness
	        XYItemRenderer r = xyplot.getRenderer(); 
	        BasicStroke wideLine = new BasicStroke(4.0f); 
	        
	        int totalSeries = xydataset.getSeriesCount();
	        for (int i=0; i<totalSeries; i++)
	        {
	        	r.setSeriesStroke(i, wideLine); 
	        }
	        
	        jfreechart.setBackgroundPaint(Color.decode("#EEFACE"));
	    	
			xyplot.setBackgroundPaint(Color.WHITE);
			xyplot.setRangeGridlinePaint(Color.BLACK);

	        return new AImage("Line Chart", createBufferedImage(jfreechart));
		}
		catch (Exception e)
		{
			SystemDictionary.webguiLog("ERROR", e.getMessage());
			e.printStackTrace();
			
			return null;
		}
    }
	
	
	/**
	 * 
	 * @param xydataset
	 * @param title
	 * @param field1
	 * @param minValue
	 * @param maxValue
	 * @return
	 */
	private AImage createOverlaidCharts(XYDataset[] xydataset, String title, String field1, String minValue, String maxValue)
    {
		try
		{
			if ((xydataset != null) && (xydataset.length == 2)) {
				// DATA serie
				IntervalXYDataset dataset1 = (IntervalXYDataset) xydataset[0];
		        XYBarRenderer renderer1 = new XYBarRenderer(0.20000000000000001D);
		        DateAxis domainAxis = new DateAxis("Date");
		        domainAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
		        NumberAxis valueAxis = new NumberAxis(field1);
		        
		        XYPlot plot = new XYPlot(dataset1, domainAxis, valueAxis, renderer1);
		        
		        // 'FLAT' series
		        XYDataset dataset2 = xydataset[1];
		        StandardXYItemRenderer renderer2 = new StandardXYItemRenderer();
		        plot.setDataset(1, dataset2);
		        plot.setRenderer(1, renderer2);
		        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		        
		        // Y min and Y max values
		        ValueAxis valueaxis = plot.getRangeAxis();
		        valueaxis.setAutoRangeMinimumSize(1.0D);
		        valueaxis.setLowerBound(Double.parseDouble(minValue));
		        valueaxis.setUpperBound(Double.parseDouble(maxValue));
		        
		        // line thickness - 'FLAT' series
		        XYItemRenderer r = plot.getRenderer(1); 
		        BasicStroke wideLine = new BasicStroke(3.0f); 
		        
		        int totalSeries = xydataset[1].getSeriesCount();
		        for (int i=0; i<totalSeries; i++)
		        {
		        	r.setSeriesStroke(i, wideLine); 
		        }
		        
		    	// colors
		        plot.setBackgroundPaint(Color.WHITE);
		        plot.setRangeGridlinePaint(Color.BLACK);
		        
		        JFreeChart jfreechart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
		        jfreechart.setBackgroundPaint(Color.decode("#EEFACE"));
		        
		        return new AImage("Line Chart", createBufferedImage(jfreechart));
			}
				
			// ERROR

	        return null;
		}
		catch (Exception e)
		{
			SystemDictionary.webguiLog("ERROR", e.getMessage());
			e.printStackTrace();
			
			return null;
		}
    }

	
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * 
	 * @param l
	 * @return
	 */
	private XYDataset createOneSerieMeasurementsDataset(List<Measurement> l, String title) {
		try
		{
			TimeSeries timeseries = new TimeSeries(title);
			
			Iterator<Measurement> it = l.iterator();
			while (it.hasNext())
			{
				Measurement mnext = it.next();
				timeseries.add(getRegularTimePeriodDay(mnext.getDateTime().toGregorianCalendar()), mnext.getValue());
			}
			
			TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
			timeseriescollection.addSeries(timeseries);
			return timeseriescollection;
		}
		catch (Exception e)
		{
			SystemDictionary.webguiLog("ERROR", e.getMessage());
			e.printStackTrace();
			
			return null;
		}
	}
	
	
	/**
	 * 
	 * @param l
	 * @param title1
	 * @param title2
	 * @return
	 */
	private XYDataset createTwoSeriesMeasurementsDataset(List<Measurement>[] l, String title1, String title2) {
		try
		{
			SystemDictionary.webguiLog("DEBUG", "createTwoSeriesMeasurementsDataset... ");
			
			TimeSeries timeseries1 = new TimeSeries(title1);
			TimeSeries timeseries2 = new TimeSeries(title2);
			
			Iterator<Measurement> it1 = l[0].iterator();
			while (it1.hasNext())
			{
				Measurement mnext = it1.next();
				timeseries1.add(getRegularTimePeriodDay(mnext.getDateTime().toGregorianCalendar()), mnext.getValue());
				SystemDictionary.webguiLog("DEBUG", "it1.hasNext() " + mnext.getValue());
			}
			
			Iterator<Measurement> it2 = l[1].iterator();
			while (it2.hasNext())
			{
				Measurement mnext = it2.next();
				timeseries2.add(getRegularTimePeriodDay(mnext.getDateTime().toGregorianCalendar()), mnext.getValue());
				SystemDictionary.webguiLog("DEBUG", "it2.hasNext() " + mnext.getValue());
			}
			
			TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
			timeseriescollection.addSeries(timeseries1);
			timeseriescollection.addSeries(timeseries2);
			return timeseriescollection;
		}
		catch (Exception e)
		{
			SystemDictionary.webguiLog("ERROR", e.getMessage());
			e.printStackTrace();
			
			return null;
		}
	}
	
	
	/**
	 * 
	 * @param l
	 * @param title1
	 * @return
	 */
	private XYDataset[] createSerieMeasurementDatasetWithFlatSeries(List<Measurement> l, String title) {
		try
		{
			TimeSeries timeseries = new TimeSeries(title);
			
			Iterator<Measurement> it = l.iterator();
			int i = 0;
			Day iniD = null;
			Day finD = null;
			while (it.hasNext())
			{
				Measurement mnext = it.next();
				Day mesD = getRegularTimePeriodDay(mnext.getDateTime().toGregorianCalendar());
				timeseries.add(mesD, mnext.getValue());
				
				if (i==0) {
					iniD = mesD;
				}
				else {
					finD = mesD;
				}
				i++;
			}
			
			TimeSeriesCollection[] timeseriescollection = new TimeSeriesCollection[2];
			timeseriescollection[0] = new TimeSeriesCollection();
			timeseriescollection[1] = new TimeSeriesCollection();
			
			// data
			timeseriescollection[0].addSeries(timeseries);
			
			// limits
			// 
			timeseriescollection[1].addSeries(createFlatDataset(iniD, finD, 5000, "ACTIVITY_LOW"));
			//
			timeseriescollection[1].addSeries(createFlatDataset(iniD, finD, 7500, "ACTIVITY_MED"));
			//
			timeseriescollection[1].addSeries(createFlatDataset(iniD, finD, 10000, "ACTIVITY_ACTIVE"));
			//
			timeseriescollection[1].addSeries(createFlatDataset(iniD, finD, 12500, "ACTIVITY_HIGH"));
			
			return timeseriescollection;
		}
		catch (Exception e)
		{
			SystemDictionary.webguiLog("ERROR", e.getMessage());
			e.printStackTrace();
			
			return null;
		}
	}
	
	
	/**
	 * 
	 * @param ini
	 * @param fin
	 * @param value
	 * @param titleFlatSerie
	 * @return
	 */
	private static TimeSeries createFlatDataset(Day ini, Day fin, int value, String titleFlatSerie) {
        TimeSeries timeseries = new TimeSeries(titleFlatSerie);
        timeseries.add(ini, value);
        timeseries.add(fin, value);
        return timeseries;
    }
	
	
	/**
	 * 
	 * @param cal
	 * @return
	 */
	private Day getRegularTimePeriodDay(GregorianCalendar cal) {
		try
		{
			return new Day(cal.getTime());
		}
		catch (Exception e)
		{
			SystemDictionary.webguiLog("ERROR", e.getMessage());
			e.printStackTrace();
			
			return null;
		}
	}
	
	
	/**
	 * 
	 * @param chart
	 * @return
	 * @throws IOException
	 */
	private byte[] createBufferedImage(JFreeChart chart) throws IOException 
	{
		BufferedImage bi = chart.createBufferedImage(_width, _height, BufferedImage.TRANSLUCENT, null);
		return EncoderUtil.encode(bi, ImageFormat.PNG, true);
	}
	

}
