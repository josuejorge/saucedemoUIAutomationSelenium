package listeners;

import evidence.EvidenceHolder;
import evidence.EvidencePdfGenerator;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.List;

public class EvidenceListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        EvidenceHolder.clear();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        generateEvidence(result, "PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        generateEvidence(result, "FAILED");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        EvidenceHolder.clear();
    }

    private void generateEvidence(ITestResult result, String status) {
        try {
            String testName = result.getTestClass().getRealClass().getSimpleName()
                    + "_" + result.getName();
            List<EvidenceHolder.EvidenceStep> steps = EvidenceHolder.getSteps();
            if (!steps.isEmpty()) {
                EvidencePdfGenerator.generate(testName, status, steps);
            }
        } finally {
            EvidenceHolder.clear();
        }
    }
}
