package careerpilot_parent.resume.generator.service;


import careerpilot_parent.resume.view.ResumeView;


public interface DocxResumeGenerator {


    byte[] generate(
            ResumeView resumeView
    );


}