package com.etouch.taf.tools.rally;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_AVI;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE;
import static org.monte.media.VideoFormatKeys.QualityKey;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.etouch.taf.util.ImageUtil;
import com.etouch.taf.util.LogUtil;



/**
 * The Class VideoRecorder.
 */
public class VideoRecorder {
	
	/** The log. */
	static Log log = LogUtil.getLog(VideoRecorder.class);
	
	/** The screen recorder. */
	private ScreenRecorder screenRecorder;
	
	/** The gc. */
	private GraphicsConfiguration gc = GraphicsEnvironment
	           .getLocalGraphicsEnvironment()
	           .getDefaultScreenDevice()
	           .getDefaultConfiguration();
	
	/** The file format. */
	private Format fileFormat = new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI);
	
	/** The screen format. */
	private Format screenFormat = new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
            CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
            DepthKey, 24, FrameRateKey, Rational.valueOf(15),
            QualityKey, 1.0f,
            KeyFrameIntervalKey, 15 * 60);
	
	/** The mouse format. */
	private Format mouseFormat = new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30));
	
	
	/** The video path. */
	private String videoPath;

	/**
	 * Instantiates a new video recorder.
	 *
	 * @param videoFilePath the video file path
	 */
	public VideoRecorder(String videoFilePath)
	{
		try{
				this.videoPath = videoFilePath;
				File videoFolder = new File(videoFilePath); 
		        
		        this.screenRecorder = new ScreenRecorder(gc, null,fileFormat, screenFormat, mouseFormat, null, videoFolder);
				  
		   }catch(AWTException e){
			   log.error(e.getMessage()); 
           }
		catch(IOException e)
        {
			log.error(e.getMessage());
        }

	}
	
	private String relativeVideoFileName;
	
	/**
	 * Instantiates a new video recorder.
	 *
	 * @param videoFileName the video file name
	 * @param videoPathLocation the video path location
	 */
	//Use this condtructor to override the video file name
	public VideoRecorder(String videoFileName, String videoPathLocation)
	{
		try{
				this.videoPath = videoPathLocation;
				this.relativeVideoFileName = videoFileName;
				File videoFolder = new File(videoPathLocation); 
				
				this.screenRecorder = new CustomScreenRecorder(gc, null,fileFormat, screenFormat, mouseFormat, null, videoFolder, videoFileName);
				
		   }catch(AWTException e){
			   log.error(e.getMessage()); 
           }
		catch(IOException e)
        {
			log.error(e.getMessage());
        }

	}
	
	
	/**
	 * Gets the screen recorder.
	 *
	 * @return the screen recorder
	 */
	public ScreenRecorder getScreenRecorder() {
		return screenRecorder;
	}

	/**
	 * Sets the screen recorder.
	 *
	 * @param screenRecorder the new screen recorder
	 */
	public void setScreenRecorder(ScreenRecorder screenRecorder) {
		this.screenRecorder = screenRecorder;
	}
	
	/**
	 * Start recording.
	 */
	public void startRecording() 
       {
	    try{ 
           	 this.screenRecorder.start();
           	 	
           }catch(IOException e)
	        {
        	   log.error(e.getMessage());
	        }
       }
 
    /**
   	 * Stop recording.
   	 */
   	public void stopRecording() 
       {
    	 try{
    		 	this.screenRecorder.stop();
    		 	
    	 	}catch(Exception e)
    	 	{
    	 		log.error(e.getMessage());
    	 	}
       }
   	
   	
   	/**
	    * Delete recording.
	    */
	   public void deleteRecording() {
		
		File fileToBeDeleted = pickLatestVideoFile(videoPath);
		fileToBeDeleted.delete();
		
	}
   	
   	public boolean deleteRecording(File videoFile){
   		return videoFile.delete();
   	}
   	
   	/**
	    * Gets the video file.
	    *
	    * @return the video file
	    */
	   public String getVideoFile()
   	{
   		String videoFileName = null;
   		File videoFile = pickLatestVideoFile(videoPath);
   		if(videoFile!=null) {
   			videoFileName = videoFile.getName();
   		}
   		return videoFileName;
   	}
   	
    
   	public File getCreatedVideoFile(){
   	  File createdVideoFile = null;
   	  
   	  List<File> videoFiles = this.screenRecorder.getCreatedMovieFiles();
   	  
   	  for(File videoFile : videoFiles){
   		  
   		  log.debug("Video file created ==>"+videoFile.getName());
   		  if(videoFile.getName().contains(relativeVideoFileName)){
   			  log.debug("File found as ==>"+videoFile.getName());
   			  createdVideoFile = videoFile;
   		  }
   	  }
   	  
   	  return createdVideoFile;
   	}
   	
	/**
	 * Pick latest video file.
	 *
	 * @param videoPath2 the video path2
	 * @return the file
	 */
	private File pickLatestVideoFile(String videoPath2) {
			File directory = new File(videoPath);
			File[] files = directory.listFiles();
			
			File fileToReturn = null;
			
			if(files!=null && files.length>0)
			{
				Arrays.sort(files, new Comparator<File>(){
				    public int compare(File f1, File f2)
				    {
				        return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
				    } });
				fileToReturn = files[files.length-1];
			}
			
			return fileToReturn;
	}

	
	/**
	 * Creates the screenshot.
	 *
	 * @param imagePath the image path
	 * @param imgName the img name
	 * @return the screenshot
	 * @throws IOException 
	 * @throws AWTException 
	 */
	public void createScreenshot(String imagePath, String imgName) throws IOException, AWTException {
		try{
		        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		        ImageIO.write(image, "jpg", new File(imagePath + File.separator + imgName + ".png"));
			      
	    	}catch(IOException e){
	    		log.error(e.getMessage());
	    		throw e;
	    		
	        }catch(AWTException e){
	        	log.error(e.getMessage());
	        	throw e;
	        }
	    }
	
	
	public void createScreenshot(String imagePath, String imgName, WebDriver driver) throws WebDriverException, IOException {
		getScreenshot(imagePath, imgName, driver);
	}
	
	public File takeScreenshot(String imagePath, String imgName, WebDriver driver) throws WebDriverException, IOException {
		
		try{
			File directoryName = new File(imagePath);	
			
			if(!directoryName.exists())
				directoryName.mkdir();
		}
		catch (Exception exception){
			log.error("Screenshot directory does not exist... Failed to create directory! "+ exception.getCause());
			throw exception;
			
		}
		
		return getScreenshot(imagePath, imgName, driver);
	}


	/**
	 * @param imagePath
	 * @param imgName
	 * @param driver
	 * @return
	 * @throws WebDriverException
	 * @throws IOException 
	 */
	private File getScreenshot(String imagePath, String imgName,
			WebDriver driver) throws WebDriverException, IOException {
		
		File screenshot = null;
		try {
               TakesScreenshot ts = (TakesScreenshot) driver;
               byte[] image = ts.getScreenshotAs(OutputType.BYTES);
               screenshot = new File(imagePath + "/" + imgName + ".png");
               FileOutputStream fos = new FileOutputStream(screenshot);
               fos.write(image);
               fos.close();
               
               // Reduce Image Size if greater than 5 MB
               ImageUtil.scale(screenshot);
               
        } catch (IOException ex) {
        	log.error("Failed to take screenshot..."+ ex.getCause());
        	throw ex;
        }
		return screenshot;
	}
	
	
	public boolean deleteScreenshot(File screenshotFile){
		return screenshotFile.delete();
	}
}