package util;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: A
 * Date: 14-7-11
 * Time: 下午2:58
 * To change this template use File | Settings | File Templates.
 */
public class ExcelReader {
    public static final String EXPECTATION_VALUE = "ExpectationValue";
    public static final String EXPECTATION_KEY = "ExpectationKey";
    /**
     * 读取excel文件，excel中不含合并单元格
     * @param path
     * @param sheetName
     * @return
     */
    public static String[][] getSingleExpectationData(String path, String sheetName) {
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            POIFSFileSystem POIStream = new POIFSFileSystem(fis);
            HSSFWorkbook workBook = new HSSFWorkbook(POIStream);
            //得到工作表
            HSSFSheet sheet1 = workBook.getSheet(sheetName);
            //得到总行数
            int rowNum = sheet1.getLastRowNum();
            List<String[]> results = new ArrayList<String[]>();

            for (int i=1;i<=rowNum;i++){
                //当前行
                HSSFRow row = sheet1.getRow(i);
                int colNum = row.getLastCellNum();
                String[] data = new String[colNum];
                //当前行所有列
                for (int j = 0; j < colNum; j++) {
                    if (row.getCell(j)==null){
                        data[j] ="";
                    }else {
                        data[j] = row.getCell(j).toString();
                    }
                }
                results.add(data);
            }
            fis.close();

            String[][] returnArray = new String[results.size()][rowNum];
            for (int i = 0; i < returnArray.length; i++) {
                returnArray[i] = (String[]) results.get(i);
            }
            return returnArray;
        }catch (Exception e){
            return null;
        }
    }


    /**
     * 第一行为列头, 获取各个列的title           getSingleExpectationData
     * @param sheet
     * @param range
     * @return
     */
    private static List<String> getKeyList(HSSFSheet sheet, CellRangeAddress range) {
        List<String> keys = new ArrayList<String>();
        HSSFRow header = sheet.getRow(range.getFirstRow());
        System.out.println("range.getLastColumn():  " + range.getLastColumn());
        for (int i = range.getFirstColumn(); i < range.getLastColumn(); i++) {
            keys.add(header.getCell(i) == null ? ("unnamed column " + i) : header.getCell(i).toString());
        }

        return keys;
    }

    /**
     * 通过sheet获取测试case(第一行是表头)
     * @param file
     * @param sheetName
     * @return
     */
    public static Object[][] getMultipleExpectationData(String file, String sheetName) {
        try {
            FileInputStream fis = new FileInputStream(file);
            POIFSFileSystem POIStream = new POIFSFileSystem(fis);
            HSSFWorkbook workBook = new HSSFWorkbook(POIStream);
            //得到工作表
            HSSFSheet sheet = workBook.getSheet(sheetName);
            return getMultipleExpectationData(file, sheetName, new CellRangeAddress(0, sheet.getLastRowNum(), 0, sheet.getRow(0).getLastCellNum()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过指定的cellange获取测试case(第一行是表头)
     * @param file
     * @param sheetName
     * @param range
     * @return
     */
    public static Object[][] getMultipleExpectationData(String file, String sheetName, CellRangeAddress range) {
        try {
            FileInputStream fis = new FileInputStream(file);
            POIFSFileSystem POIStream = new POIFSFileSystem(fis);
            HSSFWorkbook workBook = new HSSFWorkbook(POIStream);
            //得到工作表
            HSSFSheet sheet = workBook.getSheet(sheetName);
            List<String> keys = getKeyList(sheet, range);

            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            Map<String, Object> caseData = null;
            Map<String, String> expectation = null;
            for (int rowIndex = range.getFirstRow() + 1; rowIndex <= range.getLastRow(); rowIndex++) {
                HSSFRow row = sheet.getRow(rowIndex);
                if (row.getCell(0) == null || "".equals(row.getCell(0).toString())) {
                    expectation.put(String.valueOf(row.getCell(keys.indexOf(EXPECTATION_KEY))), String.valueOf(row.getCell(keys.indexOf(EXPECTATION_VALUE))));
                    continue;
                } else {
                    caseData = new HashMap<String, Object>();
                    expectation = new HashMap<String, String>();
                    caseData.put(EXPECTATION_VALUE, expectation);
                    list.add(caseData);
                    expectation.put(String.valueOf(row.getCell(keys.indexOf(EXPECTATION_KEY))), String.valueOf(row.getCell(keys.indexOf(EXPECTATION_VALUE))));
                }
                for (int index = range.getFirstColumn(); index < range.getLastColumn(); index++) {
                    if (!EXPECTATION_KEY.equals(keys.get(index).trim()) && !EXPECTATION_VALUE.equals(keys.get(index).trim())) {
                        if (row.getCell(index) != null) {
                            caseData.put(keys.get(index), row.getCell(index).toString());
                        }
                    }
                }
            }
            System.out.println("keys.size(): " + keys.size());
            Map<String, Object> map  ;
            Object[][] objects = new Object[list.size()][keys.size()-2] ;
            System.out.println("list: " + list.size() + " keys: " + keys.size());
            for (int i = 0 ; i <list.size() ; i++){
                map = list.get(i);
                for (int j = 1 ; j < keys.size()-1 ; j ++){
                    objects[i][j-1] =  map.get(keys.get(j)) == null ? ("") : map.get(keys.get(j));
                }
            }
            return objects;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



}
