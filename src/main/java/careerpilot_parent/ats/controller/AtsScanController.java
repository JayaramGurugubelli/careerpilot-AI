package careerpilot_parent.ats.controller;


import careerpilot_parent.ats.dto.request.CreateAtsScanRequest;
import careerpilot_parent.ats.dto.response.AtsScanResponse;
import careerpilot_parent.ats.service.AtsScanService;


import jakarta.validation.Valid;


import lombok.RequiredArgsConstructor;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;


import java.util.List;



@RestController
@RequestMapping("/api/student/ats")
@RequiredArgsConstructor
public class AtsScanController {



    private final AtsScanService atsScanService;



    /*
        Create ATS Scan
     */
    @PostMapping("/scan")
    public ResponseEntity<AtsScanResponse> createScan(
            @Valid @RequestBody CreateAtsScanRequest request
    ){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        atsScanService.createScan(request)
                );

    }





    /*
        Get logged-in student's ATS scans
     */
    @GetMapping("/scans")
    public ResponseEntity<List<AtsScanResponse>> getMyScans(){

        return ResponseEntity.ok(
                atsScanService.getMyScans()
        );

    }





    /*
        Get ATS scan by id
     */
    @GetMapping("/scans/{id}")
    public ResponseEntity<AtsScanResponse> getScanById(
            @PathVariable Long id
    ){

        return ResponseEntity.ok(
                atsScanService.getScanById(id)
        );

    }


}