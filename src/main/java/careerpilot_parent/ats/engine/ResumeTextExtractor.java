package careerpilot_parent.ats.engine;


import org.apache.pdfbox.Loader;
import org.apache.pdfbox.text.PDFTextStripper;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;


import org.springframework.stereotype.Component;


import java.io.InputStream;



@Component
public class ResumeTextExtractor {



    public String extract(
            InputStream inputStream,
            String fileName
    ){


        try {


            if(fileName.endsWith(".pdf")){

                return extractPdf(inputStream);

            }


            else if(fileName.endsWith(".docx")){

                return extractDocx(inputStream);

            }


            else {

                throw new RuntimeException(
                        "Unsupported resume format"
                );

            }


        }
        catch(Exception e){

            throw new RuntimeException(
                    "Resume text extraction failed",
                    e
            );

        }

    }





    private String extractPdf(
            InputStream inputStream
    )
            throws Exception{


        var document =
                Loader.loadPDF(
                        inputStream.readAllBytes()
                );


        PDFTextStripper stripper =
                new PDFTextStripper();



        String text =
                stripper.getText(document);



        document.close();


        return text;

    }





    private String extractDocx(
            InputStream inputStream
    )
            throws Exception{


        XWPFDocument document =
                new XWPFDocument(
                        inputStream
                );


        XWPFWordExtractor extractor =
                new XWPFWordExtractor(
                        document
                );


        String text =
                extractor.getText();



        document.close();


        return text;

    }

}