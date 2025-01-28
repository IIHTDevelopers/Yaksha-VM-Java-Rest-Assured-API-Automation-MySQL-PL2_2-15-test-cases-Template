package testcases;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import rest.CustomResponse;

public class TestCodeValidator {

	// Method to validate if specific keywords are used in the method's source code
	public static boolean validateTestMethodFromFile(String filePath, String methodName, List<String> keywords)
			throws IOException {
		// Read the content of the test class file
		String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));

		// Extract the method body for the specified method using regex
		String methodRegex = "(public\\s+CustomResponse\\s+" + methodName + "\\s*\\(.*?\\)\\s*\\{)([\\s\\S]*?)}";
		Pattern methodPattern = Pattern.compile(methodRegex);
		Matcher methodMatcher = methodPattern.matcher(fileContent);

		if (methodMatcher.find()) {

			String methodBody = fetchBody(filePath, methodName);

			// Now we validate the method body for the required keywords
			boolean allKeywordsPresent = true;

			// Loop over the provided keywords and check if each one is present in the
			// method body
			for (String keyword : keywords) {
				Pattern keywordPattern = Pattern.compile("\\b" + keyword + "\\s*\\(");
				if (!keywordPattern.matcher(methodBody).find()) {
					System.out.println("'" + keyword + "()' is missing in the method.");
					allKeywordsPresent = false;
				}
			}

			return allKeywordsPresent;

		} else {
			System.out.println("Method " + methodName + " not found in the file.");
			return false;
		}
	}

	// This method takes the method name as an argument and returns its body as a
	// String.
	public static String fetchBody(String filePath, String methodName) {
		StringBuilder methodBody = new StringBuilder();
		boolean methodFound = false;
		boolean inMethodBody = false;
		int openBracesCount = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				// Check if the method is found by matching method signature
				if (line.contains("public CustomResponse " + methodName + "(")
						|| line.contains("public String " + methodName + "(")
						|| line.contains("public Response " + methodName + "(")) {
					methodFound = true;
				}

				// Once the method is found, start capturing lines
				if (methodFound) {
					if (line.contains("{")) {
						inMethodBody = true;
						openBracesCount++;
					}

					// Capture the method body
					if (inMethodBody) {
						methodBody.append(line).append("\n");
					}

					// Check for closing braces to identify the end of the method
					if (line.contains("}")) {
						openBracesCount--;
						if (openBracesCount == 0) {
							break; // End of method body
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return methodBody.toString();
	}

	public static boolean validateResponseFields(String methodName, CustomResponse customResponse) {
		boolean isValid = true;

		switch (methodName) {
		case "getAllStocks":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields = List.of("Status");

			// Initialize an array to track validity (since arrays are mutable, we can
			// modify their contents inside lambdas)
			final boolean[] isValid1 = { true };

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid1[0] = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField == null || !statusField.equals("OK")) {
				isValid1[0] = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate ItemId, ItemName, and GenericName fields inside Results
			List<Map<String, Object>> results = customResponse.getListResults();
			results.forEach(result -> {
				if (result.get("ItemId") == null || result.get("ItemId").toString().isEmpty()) {
					isValid1[0] = false;
					System.out.println("ItemId is missing or invalid in one of the stock items.");
				}
				if (result.get("ItemName") == null || result.get("ItemName").toString().isEmpty()) {
					isValid1[0] = false;
					System.out.println("ItemName is missing or invalid in one of the stock items.");
				}
				if (result.get("GenericName") == null || result.get("GenericName").toString().isEmpty()) {
					isValid1[0] = false;
					System.out.println("GenericName is missing or invalid in one of the stock items.");
				}
			});

			// After all validations, you can use isValid[0] to check if everything passed
			if (isValid1[0]) {
				System.out.println("All validations passed.");
			} else {
				System.out.println("Some validations failed.");
			}

			break;

		case "getMainStore":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields1 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields1) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField1 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField1 == null || !statusField1.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate StoreId field inside Results
			Object storeIdObj = customResponse.getResponse().jsonPath().get("Results.StoreId");
			if (storeIdObj == null || !(storeIdObj instanceof Integer) || (Integer) storeIdObj == 0) {
				isValid = false;
				System.out.println("StoreId is missing or invalid in the response.");
			}

			break;

		case "getRequisitionByDateRange":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields11 = List.of("Status");

			// Initialize a boolean flag
			boolean isValid13 = true;

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields11) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid13 = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField11 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField11 == null || !statusField11.equals("OK")) {
				isValid13 = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate RequisitionId uniqueness inside Results
			List<Map<String, Object>> requisitionList = customResponse.getListResults();
			List<Integer> requisitionIds = requisitionList.stream()
					.map(requisition -> (Integer) requisition.get("RequisitionId")).collect(Collectors.toList());
			Set<Integer> uniqueRequisitionIds = new HashSet<>(requisitionIds);
			if (uniqueRequisitionIds.size() != requisitionIds.size()) {
				isValid13 = false;
				System.out.println("RequisitionIds are not unique.");
			}

			// Validate RequisitionNo and RequisitionStatus fields inside Results
			for (Map<String, Object> requisition : requisitionList) {
				if (requisition.get("RequistionNo") == null) {
					isValid13 = false;
					System.out.println("RequistionNo is missing in one of the requisitions.");
				}
				if (requisition.get("RequisitionStatus") == null) {
					isValid13 = false;
					System.out.println("RequisitionStatus is missing in one of the requisitions.");
				}
			}

			// After all validations, you can check if the validation passed
			if (isValid13) {
				System.out.println("All validations passed.");
			} else {
				System.out.println("Some validations failed.");
			}

			break;

		case "getPatientConsumptions":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields14 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields14) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField14 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField14 == null || !statusField14.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate PatientId and HospitalNo fields inside Results
			List<Map<String, Object>> patientConsumptions = customResponse.getListResults();
			for (Map<String, Object> patientConsumption : patientConsumptions) {
				if (patientConsumption.get("PatientId") == null) {
					isValid = false;
					System.out.println("PatientId is missing in one of the consumptions.");
				}
				if (patientConsumption.get("HospitalNo") == null) {
					isValid = false;
					System.out.println("HospitalNo is missing in one of the consumptions.");
				}
			}

			break;

		case "getPatientConsumptionInfoByPatientIdAndVisitId":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields15 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields15) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField6 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField6 == null || !statusField6.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate PatientId and PatientVisitId fields inside Results
			Map<String, Object> patientConsumption = customResponse.getMapResults();
			if (patientConsumption.get("PatientId") == null) {
				isValid = false;
				System.out.println("PatientId is missing in the response.");
			}
			if (patientConsumption.get("PatientVisitId") == null) {
				isValid = false;
				System.out.println("PatientVisitId is missing in the response.");
			}

			break;

		case "getBillingSchemeBySchemeId":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields6 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields6) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField61 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField61 == null || !statusField61.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate SchemeId and SchemeCode fields inside Results
			Map<String, Object> billingScheme = customResponse.getMapResults();
			if (billingScheme.get("SchemeId") == null) {
				isValid = false;
				System.out.println("SchemeId is missing in the response.");
			}
			if (billingScheme.get("SchemeCode") == null) {
				isValid = false;
				System.out.println("SchemeCode is missing in the response.");
			}

			break;

		case "getBillingSummaryByPatientId":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields7 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields7) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField7 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField7 == null || !statusField7.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate PatientId and TotalDue fields inside Results
			Map<String, Object> billingSummary = customResponse.getMapResults();
			if (billingSummary.get("PatientId") == null) {
				isValid = false;
				System.out.println("PatientId is missing in the response.");
			}
			if (billingSummary.get("TotalDue") == null) {
				isValid = false;
				System.out.println("TotalDue is missing in the response.");
			}

			break;

		case "getConsumptionsListOfAPatientById":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields8 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields8) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField8 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField8 == null || !statusField8.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate PatientConsumptionId uniqueness inside Results
			List<Map<String, Object>> consumptionsList = customResponse.getListResults();
			List<Integer> patientConsumptionIds = consumptionsList.stream()
					.map(consumption -> (Integer) consumption.get("PatientConsumptionId")).collect(Collectors.toList());
			Set<Integer> uniqueIds = new HashSet<>(patientConsumptionIds);
			if (patientConsumptionIds.size() != uniqueIds.size()) {
				isValid = false;
				System.out.println("PatientConsumptionId is not unique.");
			}

			// Validate TotalAmount fields inside Results
			for (Map<String, Object> consumption : consumptionsList) {
				if (consumption.get("TotalAmount") == null) {
					isValid = false;
					System.out.println("TotalAmount is missing in one of the consumptions.");
				}
			}

			break;

		case "getReturnConsumptionsList":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields9 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields9) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField9 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField9 == null || !statusField9.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate ConsumptionReturnReceiptNo uniqueness inside Results
			List<Map<String, Object>> returnConsumptions = customResponse.getListResults();
			List<Integer> consumptionReturnReceiptNos = returnConsumptions.stream()
					.map(consumption -> (Integer) consumption.get("ConsumptionReturnReceiptNo"))
					.collect(Collectors.toList());
			Set<Integer> uniqueReceiptNos = new HashSet<>(consumptionReturnReceiptNos);
			if (consumptionReturnReceiptNos.size() != uniqueReceiptNos.size()) {
				isValid = false;
				System.out.println("ConsumptionReturnReceiptNo is not unique.");
			}

			// Validate PatientId fields inside Results
			for (Map<String, Object> consumption : returnConsumptions) {
				if (consumption.get("PatientId") == null) {
					isValid = false;
					System.out.println("PatientId is missing in one of the consumptions.");
				}
			}

			break;

		case "getDischargedPatients":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields10 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields10) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField10 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField10 == null || !statusField10.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate PatientVisitId and PatientAdmissionId uniqueness inside Results
			List<Map<String, Object>> dischargedPatients = customResponse.getListResults();
			List<Integer> patientVisitIds = dischargedPatients.stream()
					.map(patient -> (Integer) patient.get("PatientVisitId")).collect(Collectors.toList());
			Set<Integer> uniquePatientVisitIds = new HashSet<>(patientVisitIds);
			if (patientVisitIds.size() != uniquePatientVisitIds.size()) {
				isValid = false;
				System.out.println("PatientVisitId is not unique.");
			}

			List<Integer> patientAdmissionIds = dischargedPatients.stream()
					.map(patient -> (Integer) patient.get("PatientAdmissionId")).collect(Collectors.toList());
			Set<Integer> uniquePatientAdmissionIds = new HashSet<>(patientAdmissionIds);
			if (patientAdmissionIds.size() != uniquePatientAdmissionIds.size()) {
				isValid = false;
				System.out.println("PatientAdmissionId is not unique.");
			}

			// Validate PatientId fields inside Results
			for (Map<String, Object> patient : dischargedPatients) {
				if (patient.get("PatientId") == null) {
					isValid = false;
					System.out.println("PatientId is missing in one of the patients.");
				}
			}

			break;

		case "getFilmTypesInRadiology":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields101 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields101) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField101 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField101 == null || !statusField101.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate FilmTypeId uniqueness inside Results
			List<Map<String, Object>> results101 = customResponse.getListResults();
			List<Integer> filmTypeIds = results101.stream().map(result -> (Integer) result.get("FilmTypeId"))
					.collect(Collectors.toList());
			Set<Integer> uniqueFilmTypeIds = new HashSet<>(filmTypeIds);
			if (filmTypeIds.size() != uniqueFilmTypeIds.size()) {
				isValid = false;
				System.out.println("FilmTypeId is not unique.");
			}

			// Validate FilmType fields inside Results
			for (Map<String, Object> result : results101) {
				if (result.get("FilmType") == null) {
					isValid = false;
					System.out.println("FilmType is missing in one of the results.");
				}
			}

			break;

		case "getRequisitionsByOrderStatusAndDateRange":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields12 = List.of("Status");

			// Retrieve the orderStatus value (you can pass it into the case statement
			// context)
			String expectedOrderStatus = "active"; // Make sure this value matches the orderStatus passed in the test

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields12) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField12 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField12 == null || !statusField12.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate ImagingRequisitionId uniqueness inside Results
			List<Map<String, Object>> results12 = customResponse.getListResults();
			List<Integer> imagingRequisitionIds = results12.stream()
					.map(result -> (Integer) result.get("ImagingRequisitionId")).collect(Collectors.toList());
			Set<Integer> uniqueImagingRequisitionIds = new HashSet<>(imagingRequisitionIds);
			if (imagingRequisitionIds.size() != uniqueImagingRequisitionIds.size()) {
				isValid = false;
				System.out.println("ImagingRequisitionId is not unique.");
			}

			// Validate CreatedOn date inside Results
			for (Map<String, Object> result : results12) {
				if (result.get("CreatedOn") == null) {
					isValid = false;
					System.out.println("CreatedOn is missing in one of the results.");
				}
			}

			break;

		case "getImagingReportsWithStatusAndDateRange":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields13 = List.of("Status");

			// Retrieve the expected date range values
			String expectedFromDate = "2024-01-14"; // You can dynamically pass this value from the test
			String expectedToDate = "2025-01-21"; // You can dynamically pass this value from the test

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields13) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField13 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField13 == null || !statusField13.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate ImagingRequisitionId uniqueness inside Results
			List<Map<String, Object>> results13 = customResponse.getListResults();
			List<Integer> imagingRequisitionIds13 = results13.stream()
					.map(result -> (Integer) result.get("ImagingRequisitionId")).collect(Collectors.toList());
			Set<Integer> uniqueImagingRequisitionIds13 = new HashSet<>(imagingRequisitionIds13);
			if (imagingRequisitionIds13.size() != uniqueImagingRequisitionIds13.size()) {
				isValid = false;
				System.out.println("ImagingRequisitionId is not unique.");
			}

			// Validate CreatedOn date inside Results
			for (Map<String, Object> result : results13) {
				String createdOn = (String) result.get("CreatedOn");
				if (createdOn == null || createdOn.compareTo(expectedFromDate) < 0
						|| createdOn.compareTo(expectedToDate) > 0) {
					isValid = false;
					System.out.println("CreatedOn date is out of range: " + createdOn);
				}
			}

			break;

		case "getAdmittedPatientData":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields1414 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields1414) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField1414 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField1414 == null || !statusField1414.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate that PatientId exists and matches expected value
			Map<String, Object> results1414 = customResponse.getMapResults();
			Map<String, Object> patient = (Map<String, Object>) results1414.get("Patient");
			if (patient.get("PatientId") == null || !patient.get("PatientId").equals(176)) {
				isValid = false;
				System.out.println("PatientId is missing or does not match the expected value.");
			}

			// Validate BillItems inside Results
			List<Map<String, Object>> billItems = (List<Map<String, Object>>) results1414.get("BillItems");
			if (billItems == null || billItems.isEmpty()) {
				isValid = false;
				System.out.println("BillItems array is missing or empty.");
			}

			// Validate that each BillItem has required fields and values
			for (Map<String, Object> item : billItems) {
				if (item.get("PatientId") == null) {
					isValid = false;
					System.out.println("PatientId is missing in one of the BillItems.");
				}
				if (item.get("Price") == null) {
					isValid = false;
					System.out.println("Price is missing in one of the BillItems.");
				}
				if (!"provisional".equals(item.get("BillStatus"))) {
					isValid = false;
					System.out.println("BillStatus is incorrect in one of the BillItems.");
				}
				if (!"inpatient".equals(item.get("BillingType"))) {
					isValid = false;
					System.out.println("BillingType is incorrect in one of the BillItems.");
				}
				if (!"inpatient".equals(item.get("VisitType"))) {
					isValid = false;
					System.out.println("VisitType is incorrect in one of the BillItems.");
				}
			}

			break;

		case "getInPatientProvItems":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields1515 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields1515) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField1515 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField1515 == null || !statusField1515.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate the BillItems array inside Results
			List<Map<String, Object>> results1515 = customResponse.getListResults();
			if (results1515 == null || results1515.isEmpty()) {
				isValid = false;
				System.out.println("BillItems array is missing or empty.");
			}

			// Validate the fields in each BillItem (without using forEach)
			for (int i = 0; i < results1515.size(); i++) {
				Map<String, Object> result = results1515.get(i);

				// Extract and validate fields
				Integer billingTransactionItemId = (Integer) result.get("BillingTransactionItemId");
				String serviceDepartmentName = (String) result.get("ServiceDepartmentName");
				String itemName = (String) result.get("ItemName");

				if (billingTransactionItemId == null) {
					isValid = false;
					System.out.println("BillingTransactionItemId is missing for object at index " + i);
				}

				if (serviceDepartmentName == null) {
					isValid = false;
					System.out.println("ServiceDepartmentName is missing for object at index " + i);
				}

				if (itemName == null) {
					isValid = false;
					System.out.println("ItemName is missing for object at index " + i);
				}
			}

			break;

		case "getDoctorsList":
			// List of fields to check at the top level
			List<String> expectedTopLevelFields16 = List.of("Status");

			// Print the response for debugging
			System.out.println(customResponse.getResponse().prettyPrint());

			// Validate the response structure for required top-level fields
			for (String field : expectedTopLevelFields16) {
				if (customResponse.getResponse().jsonPath().get(field) == null) {
					isValid = false;
					System.out.println("Missing field in response: " + field);
				}
			}

			// Validate the Status field at the top level
			String statusField16 = customResponse.getResponse().jsonPath().getString("Status");
			if (statusField16 == null || !statusField16.equals("OK")) {
				isValid = false;
				System.out.println("Status field is missing or invalid in the response.");
			}

			// Validate the Results array inside the response
			List<Map<String, Object>> results16 = customResponse.getListResults();
			if (results16 == null || results16.isEmpty()) {
				isValid = false;
				System.out.println("Results array is missing or empty.");
			}

			// Validate each doctor in the Results (without using forEach loop)
			for (int i = 0; i < results16.size(); i++) {
				Map<String, Object> result = results16.get(i);

				// Extract and validate fields
				Integer employeeId = (Integer) result.get("EmployeeId");
				String firstName = (String) result.get("FirstName");
				String lastName = (String) result.get("LastName");

				if (employeeId == null) {
					isValid = false;
					System.out.println("EmployeeId is missing for object at index " + i);
				}

				if (firstName == null) {
					isValid = false;
					System.out.println("FirstName is missing for object at index " + i);
				}

				if (lastName == null) {
					isValid = false;
					System.out.println("LastName is missing for object at index " + i);
				}
			}

			break;

		default:
			System.out.println("Method " + methodName + " is not recognized for validation.");
			isValid = false;
		}
		return isValid;
	}

}