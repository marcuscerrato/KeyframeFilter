package intermidia;

import java.io.File;

import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.convolution.FGaussianConvolve;
import org.openimaj.video.processing.shotdetector.VideoKeyframe;

import TVSSUnits.Shot;
import TVSSUnits.ShotList;
import TVSSUtils.KeyframeReader;

public class KeyframeFilter
{
	/*Usage: <in: keyframe folder> <out: filtered keyframe folder>*/
    public static void main( String[] args ) throws Exception
    {
    	String inputFolder = args[0];
    	String outputFolder = args[1];
    	
    	ShotList shotList = KeyframeReader.readFromFolder(inputFolder);
    		
    	int shotNum = 0;
    	for(Shot shot: shotList.getList())
    	{
    		int keyframeNum = 0;
    		for(VideoKeyframe<MBFImage> keyframe: shot.getKeyFrameList())
    		{ 
        		MBFImage filteredImage = keyframe.imageAtBoundary.processInplace(
        				new FGaussianConvolve(2f));
        		String keyframeNumber = String.format("%04d", keyframeNum);
        		String shotNumber = String.format("%04d", shotNum);
        		String outputName = "s" + shotNumber + "kf" + keyframeNumber + ".jpg";
        		ImageUtilities.write(filteredImage, new File(outputFolder + outputName));
        		keyframeNum++;
    		}
    		shotNum++;
    	}
    }
}
