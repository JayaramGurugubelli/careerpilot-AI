package careerpilot_parent.ats.extractor;

import careerpilot_parent.ats.extractor.JobKeyword;

import java.util.List;

public interface JobKeywordExtractor {

    List<JobKeyword> extractKeywords(
            String jobDescription
    );

}
