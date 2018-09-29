import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException; 

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream; 

public class TiffToJPEGByImageIO {	
    public static void main(String[] args){	
        TiffToJPEGByImageIO tiff = new TiffToJPEGByImageIO();
        tiffToJPEGByImageIO(args[0],args[1]);	
    } 	
    private static void tiffToJPEGByImageIO(String tiff,String path) {		
        ImageInputStream input;		
        try {			
            input = ImageIO.createImageInputStream(new File(tiff));//以图片输入流形式读取到tif		    
            // Get the reader	
            System.out.println(input);    
            ImageReader reader = ImageIO.getImageReaders(input).next();//获得image阅读器，阅读对象为tif文件转换的流		    
            String tiffName;			
            //path = tiff.substring(0, tiff.lastIndexOf("/"));	
            System.out.println(path);		
            tiffName = tiff.substring(tiff.lastIndexOf("/"),tiff.lastIndexOf("."));	
            System.out.println(tiffName);		    
            try {		        
                reader.setInput(input);		        
                // Read page 2 of the TIFF file		        
                int count = reader.getNumImages(true);//tif文件页数		        
                System.out.println(count);		        
                for(int i = 0; i < count; i++){		        	
                    BufferedImage image = reader.read(i, null);//取得第i页		        	
                    File f = new File(path,"/"+tiffName+"_"+i+".jpg");		        	
                    try {		        		
                        if(!f.exists()){		        			
                            f.getParentFile().mkdirs();		        			
                            f.createNewFile();		        		
                        }		        	
                    } catch (IOException e) {		        		
                        e.printStackTrace();		        	
                    }		        	
                    ImageIO.write(image, "JPEG", f);//保存图片		        
                }		    
            }		    
            finally {		       
                reader.dispose();		        
                input.close();		    
            }		
        }catch (IOException e) {			
            e.printStackTrace();		
        }	
    }
}
/*首先需要编译: javac TiffToJPEGByImageIO.java -encoding utf8
  将jai-imageio-core-1.3.0.1.jar放到java查找的jar包位置，并增加环境变量CLASSPATH
  将TiffToJPEGByImageIO.class 放到java执行的class目录，保证java执行的时候找到该class
  执行 java TiffToJPEGByImageIO tiff绝对路径  执行结果要放到的路径
*/ 