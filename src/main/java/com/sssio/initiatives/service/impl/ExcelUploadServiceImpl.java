package com.sssio.initiatives.service.impl;

import com.sssio.initiatives.repository.ActivityDAO;
import com.sssio.initiatives.request.ActivityRegistrationReq;
import com.sssio.initiatives.service.ExcelUploadService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class ExcelUploadServiceImpl implements ExcelUploadService {

    private static Logger logger = LoggerFactory.getLogger(ExcelUploadServiceImpl.class);

    @Autowired
    private ActivityDAO activityDAO;

    @Override
    public void processExcel(MultipartFile file) throws IOException {
        try(InputStream is = file.getInputStream();

            Workbook workbook = new XSSFWorkbook(is)){
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            // First Row = Activity Info
            Row activityRow = rows.next();
            String activityName = activityRow.getCell(0).getStringCellValue().trim();
            String activityDesc = activityRow.getCell(1).getStringCellValue().trim();

            String user = "SSSIO_SYSTEM";

            ActivityRegistrationReq activityRegistrationReq = getActivityRegistraReq(activityName, activityDesc);

            Long activityId = activityDAO.saveOrUpdateActivity(activityRegistrationReq, user);

            // Second Row = Questions
            Row headerRow = rows.next();
            List<String> questions = new ArrayList<>();
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                questions.add(headerRow.getCell(i).getStringCellValue().trim());
            }

            //Map<String, Long> questionIdMap = getOrCreateQuestions(activityId, questions);

            // Remaining Rows = Responses
            while (rows.hasNext()) {
                Row row = rows.next();
                //Long responseId = insertResponse(activityId);

//                for (int i = 0; i < questions.size(); i++) {
//                    String ans = row.getCell(i) != null ? row.getCell(i).toString() : "";
//                    insertResponseDetail(responseId, questionIdMap.get(questions.get(i)), ans);
//                }
            }
        }
    }

    public ActivityRegistrationReq getActivityRegistraReq(String activityName, String activityDesc){
        ActivityRegistrationReq activityRegistrationReq = new ActivityRegistrationReq();


        return activityRegistrationReq;
    }
}
