package common;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MvcFileRenamePolicy implements FileRenamePolicy {
/**
 * 벚꽃.jpg -> 20210406090909_123.jpg 파일명 변경
 */
	@Override
	public File rename(File f) {
		File newFile = null;
		
		do {
			//새파일명 생성
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS_");
			DecimalFormat df = new DecimalFormat("000");
			
			//확장자구하기
			String oldName = f.getName();
			String ext = "";
			int dot = oldName.lastIndexOf(".");
			if(dot > -1) ext = oldName.substring(dot); // .jpg
			
			String newName = sdf.format(new Date())
					       + df.format(Math.random() * 999)
					       + ext;
			
			//파일객체로 변환
			newFile = new File(f.getParent(), newName);
			
			
		}while(!createNewFile(newFile));
			 
			 return newFile;
		
	}
	/**
	 * f.createNewFile();();
	 * f가 실재 존재하지 않으면, 파일생성루 true 리턴
	 * f가 이미 존재하면 파일을 생성하지 않고 IOException을 던짐/
	 * @param f
	 * @return
	 */

	private boolean createNewFile(File f) {
		try {
			return f.createNewFile();
		} catch (IOException ignored) {
			return false;
		}
	}

}
