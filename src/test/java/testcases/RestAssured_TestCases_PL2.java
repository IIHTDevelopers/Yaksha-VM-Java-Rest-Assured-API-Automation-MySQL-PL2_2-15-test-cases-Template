package testcases;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.testng.Assert;
import org.testng.annotations.Test;

import coreUtilities.utils.FileOperations;
import rest.ApiUtil;
import rest.CustomResponse;

public class RestAssured_TestCases_PL2 {

	FileOperations fileOperations = new FileOperations();

	private final String FILEPATH = "src/main/java/rest/ApiUtil.java";
	ApiUtil apiUtil;

	@Test(priority = 1, groups = { "PL2" }, description = "1. Send a GET request to get list of stocks\n"
			+ "2. Validate that all the ItemId, ItemName, and GenericName are not null.\n"
			+ "3. Verify the response status code is 200.")
	public void getAllStocksTest() throws Exception {
		apiUtil = new ApiUtil();

		// Send the GET request and get the custom response
		CustomResponse customResponse = apiUtil.getAllStocks("/PharmacyStock/AllStockDetails", null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH, "getAllStocks",
				List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful, "getAllStocks must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getAllStocks", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Validate that ItemId, ItemName, and GenericName are not null for each
		// stock
		List<Map<String, Object>> results = customResponse.getListResults();
		results.forEach(result -> {
			Assert.assertNotNull(result.get("ItemId"), "ItemId should not be null.");
			Assert.assertNotNull(result.get("ItemName"), "ItemName should not be null.");
			Assert.assertNotNull(result.get("GenericName"), "GenericName should not be null.");
		});

		// Print the response for debugging
		System.out.println("All Stocks Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 2, groups = { "PL2" }, description = "1. Send a GET request to get main store details\n"
			+ "2. Validate that StoreId is not null.\n" + "3. Verify the response status code is 200.")
	public void getMainStoreTest() throws Exception {
		apiUtil = new ApiUtil();

		// Send the GET request and get the custom response
		CustomResponse customResponse = apiUtil.getMainStore("/PharmacySettings/MainStore", null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH, "getMainStore",
				List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful, "getMainStore must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getMainStore", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Validate that StoreId is not null
		Map<String, Object> results = customResponse.getMapResults();
		Integer storeId = (Integer) results.get("StoreId");
		Assert.assertNotNull(storeId, "StoreId should not be null.");

		// Print the response for debugging
		System.out.println("Main Store Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 3, groups = { "PL2" }, description = "1. Send a GET request to get requisition list\n"
			+ "2. Validate that all the RequisitionIds are unique.\n" + "3. Verify the response status code is 200.")
	public void getRequisitionByDateRangeTest() throws Exception {
		apiUtil = new ApiUtil();
		String fromDate = "2020-01-01";
		String toDate = "2024-11-19";

		// Send the GET request and get the custom response
		CustomResponse customResponse = apiUtil.getRequisitionByDateRange(
				"/DispensaryRequisition/Dispensary/1?FromDate=" + fromDate + "&ToDate=" + toDate, null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH,
				"getRequisitionByDateRange", List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getRequisitionByDateRange must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getRequisitionByDateRange", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Validate that RequisitionIds are unique
		List<Map<String, Object>> requisitionList = customResponse.getListResults();
		List<Integer> requisitionIds = requisitionList.stream()
				.map(requisition -> (Integer) requisition.get("RequisitionId")).collect(Collectors.toList());
		Set<Integer> uniqueRequisitionIds = new HashSet<>(requisitionIds);
		Assert.assertEquals(uniqueRequisitionIds.size(), requisitionIds.size(), "RequisitionIds should be unique.");

		// 4. Validate that RequisitionNo and RequisitionStatus are not null
		requisitionList.forEach(requisition -> {
			Assert.assertNotNull(requisition.get("RequistionNo"), "RequistionNo should not be null.");
			Assert.assertNotNull(requisition.get("RequisitionStatus"), "RequisitionStatus should not be null.");
		});

		// Print the response for debugging
		System.out.println("Requisition Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 4, groups = { "PL2" }, description = "1. Send a GET request to get patient consumptions list\n"
			+ "2. Validate that PatientId and HospitalNo are not null.\n"
			+ "3. Verify the response status code is 200.")
	public void getPatientConsumptionsTest() throws Exception {
		apiUtil = new ApiUtil();

		// Send the GET request and get the custom response
		CustomResponse customResponse = apiUtil.getPatientConsumptions("/PatientConsumption/PatientConsumptions", null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH,
				"getPatientConsumptions", List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getPatientConsumptions must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getPatientConsumptions", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Validate that PatientId and HospitalNo are not null
		List<Map<String, Object>> patientConsumptions = customResponse.getListResults();
		for (Map<String, Object> patientConsumption : patientConsumptions) {
			Assert.assertNotNull(patientConsumption.get("PatientId"), "PatientId should not be null.");
			Assert.assertNotNull(patientConsumption.get("HospitalNo"), "HospitalNo should not be null.");
		}

		// Print the response for debugging
		System.out.println("Patient Consumptions Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 5, groups = {
			"PL2" }, description = "1. Send a GET request to get patient consumption information\n"
					+ "2. Validate that PatientId is the same in response\n"
					+ "3. Verify the response status code is 200.")
	public void getPatientConsumptionInfoByPatientIdAndVisitIdTest() throws Exception {
		apiUtil = new ApiUtil();
		String patientId = "114";
		String patientVisitId = "53";

		// Send the GET request and get the custom response
		CustomResponse customResponse = apiUtil
				.getPatientConsumptionInfoByPatientIdAndVisitId("/PatientConsumption/PatientConsumptionInfo?patientId="
						+ patientId + "&patientVisitId=" + patientVisitId, null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH,
				"getPatientConsumptionInfoByPatientIdAndVisitId", List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getPatientConsumptionInfoByPatientIdAndVisitId must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getPatientConsumptionInfoByPatientIdAndVisitId",
				customResponse), "Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Verify PatientId is the same as the provided one
		Map<String, Object> patientConsumption = customResponse.getMapResults();
		int patientIdFromResponse = (Integer) patientConsumption.get("PatientId");
		Assert.assertEquals(String.valueOf(patientIdFromResponse), patientId, "PatientId should be " + patientId + ".");

		// 4. Validate that PatientVisitId is not null
		Integer patientVisitIdFromResponse = (Integer) patientConsumption.get("PatientVisitId");
		Assert.assertNotNull(patientVisitIdFromResponse, "PatientVisitId should not be null.");

		// Print the response for debugging
		System.out.println("Consumption Information Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 6, groups = { "PL2" }, description = "1. Send a GET request to get billing scheme by scheme ID\n"
			+ "2. Validate that SchemeId is the same in response\n" + "3. Verify the response status code is 200.")
	public void getBillingSchemeBySchemeIdTest() throws Exception {
		apiUtil = new ApiUtil();
		String schemeId = "4";

		// Send the GET request and get the custom response
		CustomResponse customResponse = apiUtil
				.getBillingSchemeBySchemeId("/PatientConsumption/PharmacyIpBillingScheme?schemeId=" + schemeId, null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH,
				"getBillingSchemeBySchemeId", List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getBillingSchemeBySchemeId must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getBillingSchemeBySchemeId", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Verify SchemeId is the same as the provided one
		Map<String, Object> billingScheme = customResponse.getMapResults();
		int schemeIdFromResponse = (Integer) billingScheme.get("SchemeId");
		Assert.assertEquals(String.valueOf(schemeIdFromResponse), schemeId, "SchemeId should be " + schemeId + ".");

		// 4. Validate that SchemeCode is not null
		String schemeCode = (String) billingScheme.get("SchemeCode");
		Assert.assertNotNull(schemeCode, "SchemeCode should not be null.");

		// Print the response for debugging
		System.out.println("Billing Scheme Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 7, groups = { "PL2" }, description = "1. Send a GET request to get billing summary by patient ID\n"
			+ "2. Validate that PatientId is the same in response\n" + "3. Verify the response status code is 200.")
	public void getBillingSummaryByPatientIdTest() throws Exception {
		apiUtil = new ApiUtil();
		String patientId = "114";

		// Send the GET request and get the custom response
		CustomResponse customResponse = apiUtil
				.getBillingSummaryByPatientId("/PharmacySales/PatientBillingSummary?patientId=" + patientId, null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH,
				"getBillingSummaryByPatientId", List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getBillingSummaryByPatientId must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getBillingSummaryByPatientId", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Verify PatientId is the same as the provided one
		Map<String, Object> billingSummary = customResponse.getMapResults();
		int patientIdFromResponse = (Integer) billingSummary.get("PatientId");
		Assert.assertEquals(String.valueOf(patientIdFromResponse), patientId, "PatientId should be " + patientId + ".");

		// 4. Validate that TotalDue is not null
		Float totalDue = (Float) billingSummary.get("TotalDue");
		Assert.assertNotNull(totalDue, "TotalDue should not be null.");

		// Print the response for debugging
		System.out.println("Billing Summary Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 8, groups = {
			"PL2" }, description = "1. Send a GET request to get consumptions list of a patient\n"
					+ "2. Validate that PatientConsumptionId is unique\n"
					+ "3. Verify the response status code is 200.")
	public void getConsumptionsListOfAPatientByIdTest() throws Exception {
		apiUtil = new ApiUtil();
		String patientId = "114";
		String patientVisitId = "53";

		// Send the GET request and get the custom response
		CustomResponse customResponse = apiUtil
				.getConsumptionsListOfAPatientById("/PatientConsumption/ConsumptionsOfPatient?patientId=" + patientId
						+ "&patientVisitId=" + patientVisitId, null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH,
				"getConsumptionsListOfAPatientById", List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getConsumptionsListOfAPatientById must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getConsumptionsListOfAPatientById", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Verify PatientConsumptionId is unique
		List<Map<String, Object>> consumptionsList = customResponse.getListResults();
		List<Integer> patientConsumptionIds = consumptionsList.stream()
				.map(consumption -> (Integer) consumption.get("PatientConsumptionId")).collect(Collectors.toList());
		Set<Integer> uniqueIds = new HashSet<>(patientConsumptionIds);
		Assert.assertEquals(patientConsumptionIds.size(), uniqueIds.size(), "PatientConsumptionId should be unique.");

		// 4. Validate that TotalAmount is not null
		consumptionsList.forEach(consumption -> {
			Assert.assertNotNull(consumption.get("TotalAmount"), "TotalAmount should not be null.");
		});

		// Print the response for debugging
		System.out.println("Consumptions Of Patient Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 9, groups = { "PL2" }, description = "1. Send a GET request to get return consumptions list\n"
			+ "2. Validate that ConsumptionReturnReceiptNo is unique\n" + "3. Verify the response status code is 200.")
	public void getReturnConsumptionsListTest() throws Exception {
		apiUtil = new ApiUtil();

		// Send the GET request and get the custom response
		CustomResponse customResponse = apiUtil.getReturnConsumptionsList("/PatientConsumption/Returns", null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH,
				"getReturnConsumptionsList", List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getReturnConsumptionsList must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getReturnConsumptionsList", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Verify ConsumptionReturnReceiptNo is unique
		List<Map<String, Object>> returnConsumptions = customResponse.getListResults();
		List<Integer> consumptionReturnReceiptNos = returnConsumptions.stream()
				.map(consumption -> (Integer) consumption.get("ConsumptionReturnReceiptNo"))
				.collect(Collectors.toList());
		Set<Integer> uniqueReceiptNos = new HashSet<>(consumptionReturnReceiptNos);
		Assert.assertEquals(consumptionReturnReceiptNos.size(), uniqueReceiptNos.size(),
				"ConsumptionReturnReceiptNo should be unique.");

		// 4. Validate that PatientId is not null
		for (Map<String, Object> consumption : returnConsumptions) {
			Assert.assertNotNull(consumption.get("PatientId"), "PatientId should not be null.");
		}

		// Print the response for debugging
		System.out.println("Consumption Return Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 10, groups = { "PL2" }, description = "1. Send a GET request to get discharged patients list\n"
			+ "2. Validate that PatientVisitId and PatientAdmissionId are unique\n"
			+ "3. Verify the response status code is 200.")
	public void getDischargedPatientsTest() throws Exception {
		apiUtil = new ApiUtil();
		String fromDate = "2020-01-01";
		String toDate = "2024-11-19";

		// Send the GET request and get the custom response
		CustomResponse customResponse = apiUtil.getDischargedPatients(
				"/Admission/DischargedPatients?admissionStatus=discharged&FromDate=" + fromDate + "&ToDate=" + toDate,
				null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH, "getDischargedPatients",
				List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getDischargedPatients must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getDischargedPatients", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Verify PatientVisitId and PatientAdmissionId are unique
		List<Map<String, Object>> dischargedPatients = customResponse.getListResults();
		List<Integer> patientVisitIds = dischargedPatients.stream()
				.map(patient -> (Integer) patient.get("PatientVisitId")).collect(Collectors.toList());
		Set<Integer> uniquePatientVisitIds = new HashSet<>(patientVisitIds);
		Assert.assertEquals(patientVisitIds.size(), uniquePatientVisitIds.size(), "PatientVisitId should be unique.");

		List<Integer> patientAdmissionIds = dischargedPatients.stream()
				.map(patient -> (Integer) patient.get("PatientAdmissionId")).collect(Collectors.toList());
		Set<Integer> uniquePatientAdmissionIds = new HashSet<>(patientAdmissionIds);
		Assert.assertEquals(patientAdmissionIds.size(), uniquePatientAdmissionIds.size(),
				"PatientAdmissionId should be unique.");

		// 4. Validate that PatientId is not null
		for (Map<String, Object> patient : dischargedPatients) {
			Assert.assertNotNull(patient.get("PatientId"), "PatientId should not be null.");
		}

		// Print the response for debugging
		System.out.println("Discharged Patients Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 11, groups = { "PL2" }, description = "Send a get request to get all the Film Types\n"
			+ "Validate the Status is 'OK'\n" + "Validate the Status code is 200\n"
			+ "Validate the FilmTypeId is not null and unique\n" + "Validate the FilmType is not null")
	public void validateFilmTypesApiResponseTest() throws Exception {
		apiUtil = new ApiUtil();

		// Send the GET request and get the custom response
		CustomResponse customResponse = apiUtil.getFilmTypesInRadiology("/radiology/FilmTypes", null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH,
				"getFilmTypesInRadiology", List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getFilmTypesInRadiology must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getFilmTypesInRadiology", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Verify Results array is not null or empty
		List<Map<String, Object>> results = customResponse.getListResults();
		Assert.assertNotNull(results, "Results should not be null.");
		Assert.assertFalse(results.isEmpty(), "Results should not be empty.");

		// 4. Verify FilmTypeId is not null and unique
		Set<Integer> filmTypeIds = new HashSet<>();
		for (Map<String, Object> result : results) {
			Integer filmTypeId = (Integer) result.get("FilmTypeId");
			Assert.assertNotNull(filmTypeId, "FilmTypeId should not be null.");
			Assert.assertTrue(filmTypeIds.add(filmTypeId),
					"FilmTypeId should be unique. Duplicate found: " + filmTypeId);
		}

		// 5. Verify FilmType is not null
		for (Map<String, Object> result : results) {
			String filmType = (String) result.get("FilmType");
			Assert.assertNotNull(filmType, "FilmType should not be null.");
		}

		// Print the response for debugging
		System.out.println("Film Types API Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 12, groups = {
			"PL2" }, description = "Send a get request with fromDate, toDate, orderStatus, reportOrderStatus, and typeList parameters to get requisitions\n"
					+ "Validate the createdOn date is not null\n"
					+ "Validate the OrderStatus matches with the provided orderStatus\n"
					+ "Validate the Status is 'OK'\n" + "Validate the Status code is 200")
	public void validateImagingItemsApiResponseTest() throws Exception {
		apiUtil = new ApiUtil();

		String fromDate = "2024-01-15";
		String toDate = "2025-01-20";
		String orderStatus = "active";
		String reportOrderStatus = "pending";
		String typeList = "[1,2,3,4]";

		// Send the GET request and get the custom response
		CustomResponse customResponse = apiUtil.getRequisitionsByOrderStatusAndDateRange(
				"/radiology/Requisitions?reqOrderStatus=" + orderStatus + "&reportOrderStatus=" + reportOrderStatus
						+ "&typeList=" + typeList + "&fromDate=" + fromDate + "&toDate=" + toDate,
				null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH,
				"getRequisitionsByOrderStatusAndDateRange", List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getRequisitionsByOrderStatusAndDateRange must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(
				TestCodeValidator.validateResponseFields("getRequisitionsByOrderStatusAndDateRange", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Verify Results array is not null or empty
		List<Map<String, Object>> results = customResponse.getListResults();
		Assert.assertNotNull(results, "Results should not be null.");
		Assert.assertFalse(results.isEmpty(), "Results should not be empty.");

		// 4. Verify ImagingRequisitionId exists and is unique
		Set<Integer> imagingRequisitionIds = new HashSet<>();
		for (Map<String, Object> result : results) {
			Integer imagingRequisitionId = (Integer) result.get("ImagingRequisitionId");
			Assert.assertNotNull(imagingRequisitionId, "ImagingRequisitionId should not be null.");
			Assert.assertTrue(imagingRequisitionIds.add(imagingRequisitionId),
					"ImagingRequisitionId should be unique. Duplicate found: " + imagingRequisitionId);
		}

		// 5. Verify CreatedOn date is not null
		for (Map<String, Object> result : results) {
			String createdOn = (String) result.get("CreatedOn");
			Assert.assertNotNull(createdOn, "CreatedOn date should not be null.");
		}

		// Print the response for debugging
		System.out.println("Requisition status items API Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 13, groups = {
			"PL2" }, description = "Send a get request with the fromDate, toDate, reportOrderStatus, and typeList parameters to get imaging reports\n"
					+ "Validate the Status is 'OK'\n" + "Validate the Status code is 200\n"
					+ "Validate the CreatedOn date is within the provided date range")
	public void validateImagingReportsWithStatusAndDateRangeTest() throws Exception {
		apiUtil = new ApiUtil();

		// Define the date range (as provided in the query parameters)
		String fromDate = "2024-01-14";
		String toDate = "2025-01-21";
		String reportOrderStatus = "final";
		String typeList = "[1,2,3,4]";

		// Send the GET request and get the custom response
		CustomResponse customResponse = apiUtil.getImagingReportsWithStatusAndDateRange(
				"/radiology/ImagingReports?reportOrderStatus=" + reportOrderStatus + "&fromDate=" + fromDate
						+ "&toDate=" + toDate + "&typeList=" + typeList,
				null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH,
				"getImagingReportsWithStatusAndDateRange", List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getImagingReportsWithStatusAndDateRange must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(
				TestCodeValidator.validateResponseFields("getImagingReportsWithStatusAndDateRange", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Verify Results array is not null or empty
		List<Map<String, Object>> results = customResponse.getListResults();
		Assert.assertNotNull(results, "Results should not be null.");
		Assert.assertFalse(results.isEmpty(), "Results should not be empty.");

		// 4. Verify ImagingRequisitionId exists and is unique
		Set<Integer> imagingRequisitionIds = new HashSet<>();
		for (Map<String, Object> result : results) {
			Integer imagingRequisitionId = (Integer) result.get("ImagingRequisitionId");
			Assert.assertNotNull(imagingRequisitionId, "ImagingRequisitionId should not be null.");
			Assert.assertTrue(imagingRequisitionIds.add(imagingRequisitionId),
					"ImagingRequisitionId should be unique. Duplicate found: " + imagingRequisitionId);
		}

		// 5. Verify CreatedOn date is within the provided range
		for (Map<String, Object> result : results) {
			String createdOn = (String) result.get("CreatedOn");
			Assert.assertNotNull(createdOn, "CreatedOn date should not be null.");
			Assert.assertTrue(createdOn.compareTo(fromDate) >= 0 && createdOn.compareTo(toDate) <= 0,
					"CreatedOn date is out of range: " + createdOn);
		}

		// Print the response for debugging
		System.out.println("Imaging Reports Items API Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 14, groups = {
			"PL2" }, description = "Send GET request to get the list of admitted patients data with admission status as admitted\n"
					+ "Validate the response status code is 200 OK\n"
					+ "Validate the response contains 'Status' as 'OK'\n"
					+ "Retrieve and validate the VisitCode, PatientVisitId & PatientId from the response")
	public void getAdmittedPatientDataTest() throws Exception {
		apiUtil = new ApiUtil();
		String module = "radiology";
		int visitId = 147;

		// Send the GET request and get the custom response
		CustomResponse customResponse = apiUtil.getAdmittedPatientData(
				"/Billing/InPatientProvisionalItems?patientId=176&patientVisitId=" + visitId + "&module=" + module,
				null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH,
				"getAdmittedPatientData", List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getAdmittedPatientData must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getAdmittedPatientData", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Extract and validate the Results object
		Map<String, Object> results = customResponse.getMapResults();
		Assert.assertNotNull(results, "Results should not be null.");

		// Validate the Patient object
		Map<String, Object> patient = (Map<String, Object>) results.get("Patient");
		Assert.assertNotNull(patient, "Patient object should not be null.");
		Assert.assertEquals(patient.get("PatientId"), 176, "PatientId should match the expected value.");

		// 4. Validate the BillItems array
		List<Map<String, Object>> billItems = (List<Map<String, Object>>) results.get("BillItems");
		Assert.assertNotNull(billItems, "BillItems array should not be null.");
		Assert.assertFalse(billItems.isEmpty(), "BillItems array should not be empty.");

		// Validate each BillItem (without using forEach loop)
		for (Map<String, Object> item : billItems) {
			Assert.assertNotNull(item.get("PatientId"), "PatientId should not be null.");
			Assert.assertEquals(item.get("PatientId"), 176, "PatientId in BillItem should match the expected value.");
			Assert.assertNotNull(item.get("Price"), "Price should not be null.");
			Assert.assertEquals(item.get("BillStatus"), "provisional", "BillStatus should be 'provisional'.");
			Assert.assertEquals(item.get("BillingType"), "inpatient", "BillingType should be 'inpatient'.");
			Assert.assertEquals(item.get("VisitType"), "inpatient", "VisitType should be 'inpatient'.");
		}

		// Print the response for debugging
		System.out.println("Admitted Patient Data API Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 15, groups = {
			"PL2" }, description = "1. Send GET request to get the list of In Patient Provisional Items with data such as module, patientVisitId & patientId.\n"
					+ "2. Verify the response status code is 200 OK\n"
					+ "3. Validate the response contains 'Status' as 'OK'\n"
					+ "4. Retrieve and validate the BillingTransactionItemId, ServiceDepartmentName & ItemName from the response")
	public void getInPatientProvisionalItemsTest() throws Exception {
		apiUtil = new ApiUtil();

		// Send the GET request and get the custom response
		CustomResponse customResponse = apiUtil.getInPatientProvItems(
				"/Billing/InPatientProvisionalItems?patientId=176&patientVisitId=147&module=radiology", null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH, "getInPatientProvItems",
				List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getInPatientProvItems must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getInPatientProvItems", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Extract and validate the BillItems
		List<Map<String, Object>> results = customResponse.getListResults();
		Assert.assertNotNull(results, "BillItems should not be null.");
		Assert.assertFalse(results.isEmpty(), "BillItems should not be empty.");

		// 4. Validate each BillItem (without using forEach loop)
		for (int i = 0; i < results.size(); i++) {
			Map<String, Object> result = results.get(i);

			// Extract and validate fields
			Integer billingTransactionItemId = (Integer) result.get("BillingTransactionItemId");
			String serviceDepartmentName = (String) result.get("ServiceDepartmentName");
			String itemName = (String) result.get("ItemName");

			Assert.assertNotNull(billingTransactionItemId,
					"BillingTransactionItemId should not be null for object at index " + i);
			Assert.assertNotNull(serviceDepartmentName,
					"ServiceDepartmentName should not be null for object at index " + i);
			Assert.assertNotNull(itemName, "ItemName should not be null for object at index " + i);
		}

		// Print the response for debugging
		System.out.println("In Patient Provisional Items API Response:");
		customResponse.getResponse().prettyPrint();
	}

	@Test(priority = 16, groups = {
			"PL2" }, description = "1. Send GET request to get the list of doctors with data such as EmployeeId, FirstName & LastName.\n"
					+ "2. Verify the response status code is 200 OK\n"
					+ "3. Validate the response contains 'Status' as 'OK'\n"
					+ "4. Retrieve and validate the EmployeeId, FirstName & LastName from the response")
	public void getListOfDoctorsTest() throws Exception {
		apiUtil = new ApiUtil();

		// Send the GET request and get the custom response
		CustomResponse customResponse = apiUtil.getDoctorsList("/Billing/ListDoctors", null);

		// Validate the method's source code
		boolean isValidationSuccessful = TestCodeValidator.validateTestMethodFromFile(FILEPATH, "getDoctorsList",
				List.of("given", "then", "extract", "response"));
		Assert.assertTrue(isValidationSuccessful,
				"getDoctorsList must be implemented using Rest Assured methods only.");

		// Validate response structure
		Assert.assertTrue(TestCodeValidator.validateResponseFields("getDoctorsList", customResponse),
				"Must have all required fields in the response.");

		// 1. Verify Status code is 200
		Assert.assertEquals(customResponse.getStatusCode(), 200, "Status code should be 200.");

		// 2. Validate that Status field is "OK"
		String status = customResponse.getStatus();
		Assert.assertEquals(status, "OK", "Status should be OK.");

		// 3. Extract and validate the Results array
		List<Map<String, Object>> results = customResponse.getListResults();
		Assert.assertNotNull(results, "Results should not be null.");
		Assert.assertFalse(results.isEmpty(), "Results should not be empty.");

		// 4. Validate each doctor's information (without using forEach loop)
		for (int i = 0; i < results.size(); i++) {
			Map<String, Object> result = results.get(i);

			// Extract and validate fields
			Integer employeeId = (Integer) result.get("EmployeeId");
			String firstName = (String) result.get("FirstName");
			String lastName = (String) result.get("LastName");

			Assert.assertNotNull(employeeId, "EmployeeId should not be null for object at index " + i);
			Assert.assertNotNull(firstName, "FirstName should not be null for object at index " + i);
			Assert.assertNotNull(lastName, "LastName should not be null for object at index " + i);
		}

		// Print the response for debugging
		System.out.println("Doctors List API Response:");
		customResponse.getResponse().prettyPrint();
	}

}
