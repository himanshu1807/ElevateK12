import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sun.imageio.plugins.png.RowFilter;

public class ReadExcel {
	
	
	private static HashMap<String, Object> configHashMap = new HashMap<String, Object>();
	public static Sheet inputSheet;
	public static Row rowActual;
	public static String Filter1;
	public static String Filter2;
	public static String MaxPrice;
	public static String url;
	public static String mobileBrand;
	public static String filterName1;
	public static String filterValue1;
	public static String filterName2;
	public static String filterValue2;
	public static String screenshotPath;
	public static String outPutFile;
	public static Sheet filtersheet;
	
	
	
	public static void ReadExcel(String FilePath) throws IOException, Exception {
		String projectPath = System.getProperty("user.dir");
		String InputFilePath = projectPath + "\\" + FilePath;
		//String configsheetextension = FilenameUtils.getExtension(InputFilePath);
		
		
			//configSheet1 = ExcelUtility.GetSheet(configPath, "Config");
			inputSheet = GetxlmSheet(InputFilePath, "Input");
			filtersheet = GetxlmSheet(InputFilePath, "FilterCriteria");
			int rowCount = inputSheet.getLastRowNum()+1;
			int rowsInFilterCriteria = filtersheet.getLastRowNum()+1;
			DataFormatter format = new DataFormatter();
			
			for(int rowIndex=0;rowIndex<rowCount;rowIndex++) {
			rowActual = inputSheet.getRow(rowIndex);
			String parameterName2 = format.formatCellValue(rowActual.getCell(0));				
			String value2 = format.formatCellValue(rowActual.getCell(1));
			value2=value2.replace("\"", "");
			if(StringUtils.isNotBlank(parameterName2) || StringUtils.isNotBlank(value2))
			{
				configHashMap.put(parameterName2,value2);
			}
			}
			
			for (int rowIndex = 0; rowIndex<rowsInFilterCriteria; rowIndex++) {
				rowActual = filtersheet.getRow(rowIndex);
				String parameterName2 = format.formatCellValue(rowActual.getCell(0));				
				String value2 = format.formatCellValue(rowActual.getCell(1));
				value2=value2.replace("\"", "");
				if(StringUtils.isNotBlank(parameterName2) || StringUtils.isNotBlank(value2))
				{
					configHashMap.put(parameterName2,value2);
				}
			}
			Filter1 = configHashMap.get("Filter1").toString();
			MaxPrice = configHashMap.get("Price").toString();
			Filter2 = configHashMap.get("Filter2").toString();
			splitString(Filter1, Filter2);
			url = configHashMap.get("URL").toString();
			mobileBrand = configHashMap.get("MobileBrand").toString();
			screenshotPath = configHashMap.get("ScreenshotPath").toString();
			outPutFile = configHashMap.get("OutputCSV").toString();
	}
	
	public static XSSFSheet GetxlmSheet(String FilePath,String SheetName) throws IOException, Exception
	//public static  org.apache.poi.ss.usermodel.Sheet GetxlmSheet(String FilePath,String SheetName)  throws IOException
	{
		XSSFSheet workSheet = null;
		//org.apache.poi.ss.usermodel.Sheet workSheet=null; 
		try
		{
		InputStream myXls = new FileInputStream(FilePath);	
		@SuppressWarnings("resource")
		XSSFWorkbook wBook = new XSSFWorkbook(myXls);
		workSheet = wBook.getSheet(SheetName);	
		}	
		catch(Exception e)
		{
				return null;
		}
		return workSheet;
	}
	
	public static void splitString(String value1, String value2) {
		
		String[] filter11 = value1.split(";");
		filterName1 = filter11[0];
		filterValue1 = filter11[1];
		
		 String[] filter22 = value2.split(";");
		 filterName2 = filter22[0];
		 filterValue2 = filter22[1];
	
	}

}
