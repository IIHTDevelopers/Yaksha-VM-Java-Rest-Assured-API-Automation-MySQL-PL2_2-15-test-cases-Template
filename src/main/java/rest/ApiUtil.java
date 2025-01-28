package rest;

import java.util.List;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiUtil {

	private static final String BASE_URL = "https://healthapp.yaksha.com/api";

	/**
	 * @Test1 This method retrieves and verifies the list of stocks
	 * 
	 * @param endpoint - The API endpoint to which the GET request is sent.
	 * @param body     - Optional
	 *
	 * @return CustomResponse - The API response includes HTTP status code, status
	 *         message, and the list of stocks.
	 */
	public CustomResponse getAllStocks(String endpoint, Object body) {
		// Set up the request
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		List<Map<String, Object>> results = response.jsonPath().getList("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}

	/**
	 * @Test2 This method retrieves and verifies the details of the main store.
	 * 
	 * @param endpoint - The API endpoint to which the GET request is sent.
	 * @param body     - Optional
	 *
	 * @return CustomResponse - The API response includes HTTP status code, status
	 *         message, and details of the main store in the "Results" field,
	 *         containing details such as StoreId, ParentStoreId, Category, and
	 *         IsActive.
	 */
	public CustomResponse getMainStore(String endpoint, Object body) {
		// Set up the request
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		Map<String, Object> results = response.jsonPath().getMap("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}

	/**
	 * @Test3 This method retrieves and verifies the requisition list.
	 * 
	 * @param endpoint - The API endpoint to which the GET request is sent.
	 * @param body     - Optional
	 *
	 * @return CustomResponse - The API response includes the HTTP status code,
	 *         status message, and requisition list in the "Results" field,
	 *         containing details such as RequisitionId, RequistionNo, and
	 *         RequisitionStatus.
	 */
	public CustomResponse getRequisitionByDateRange(String endpoint, Object body) {
		// Set up the request
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		List<Map<String, Object>> requisitionList = response.jsonPath().getList("Results.requisitionList");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, requisitionList);
	}

	/**
	 * @Test4 This method retrieves and verifies the patient consumptions list.
	 * 
	 * @param endpoint - The API endpoint to which the GET request is sent.
	 * @param body     - Optional
	 *
	 * @return CustomResponse - The API response includes the HTTP status code,
	 *         status message, and patient consumptions list in the "Results" field,
	 *         containing details such as PatientId, HospitalNo, and PatientVisitId.
	 */
	public CustomResponse getPatientConsumptions(String endpoint, Object body) {
		// Set up the request
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		List<Map<String, Object>> patientConsumptions = response.jsonPath().getList("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, patientConsumptions);
	}

	/**
	 * @Test5 This method retrieves and verifies the patient consumption
	 *        information.
	 * 
	 * @param endpoint - The API endpoint to which the GET request is sent.
	 * @param body     - Optional
	 *
	 * @return CustomResponse - The API response includes the HTTP status code,
	 *         status message, and patient consumption information in the "Results"
	 *         field, containing details such as PatientName, HospitalNo, and
	 *         StoreId.
	 */
	public CustomResponse getPatientConsumptionInfoByPatientIdAndVisitId(String endpoint, Object body) {
		// Set up the request
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		Map<String, Object> patientConsumption = response.jsonPath().getMap("Results.PatientConsumption");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, patientConsumption);
	}

	/**
	 * @Test6 This method retrieves and verifies the billing scheme by scheme ID.
	 * 
	 * @param endpoint - The API endpoint to which the GET request is sent.
	 * @param body     - Optional
	 *
	 * @return CustomResponse - The API response includes HTTP status code, status
	 *         message, and the billing scheme by scheme ID in the "Results" field,
	 *         containing details such as SchemeCode, SchemeName, and CommunityName.
	 */
	public CustomResponse getBillingSchemeBySchemeId(String endpoint, Object body) {
		// Set up the request
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		Map<String, Object> billingScheme = response.jsonPath().getMap("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, billingScheme);
	}

	/**
	 * @Test7 This method retrieves and verifies the billing summary by patient ID.
	 * 
	 * @param endpoint - The API endpoint to which the GET request is sent.
	 * @param body     - Optional
	 *
	 * @return CustomResponse - The API response includes HTTP status code, status
	 *         message, and the billing summary by patient ID in the "Results"
	 *         field, containing details such as CreditAmount, ProvisionalAmt, and
	 *         TotalDue.
	 */
	public CustomResponse getBillingSummaryByPatientId(String endpoint, Object body) {
		// Set up the request
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		Map<String, Object> billingSummary = response.jsonPath().getMap("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, billingSummary);
	}

	/**
	 * @Test8 This method retrieves and verifies the consumptions list of a patient
	 *        by patient ID.
	 * 
	 * @param endpoint - The API endpoint to which the GET request is sent.
	 * @param body     - Optional
	 *
	 * @return CustomResponse - The API response includes HTTP status code, status
	 *         message, and the consumptions list of a patient in the "Results"
	 *         field, containing details such as PatientConsumptionId,
	 *         ConsumptionReceiptNo, and TotalAmount.
	 */
	public CustomResponse getConsumptionsListOfAPatientById(String endpoint, Object body) {
		// Set up the request
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		List<Map<String, Object>> consumptionsList = response.jsonPath().getList("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, consumptionsList);
	}

	/**
	 * @Test9 This method retrieves and verifies the return consumptions list.
	 * 
	 * @param endpoint - The API endpoint to which the GET request is sent.
	 * @param body     - Optional
	 *
	 * @return CustomResponse - The API response includes HTTP status code, status
	 *         message, and the return consumptions list in the "Results" field,
	 *         containing details such as ConsumptionReturnReceiptNo, HospitalNo,
	 *         and PatientId.
	 */
	public CustomResponse getReturnConsumptionsList(String endpoint, Object body) {
		// Set up the request
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		List<Map<String, Object>> returnConsumptions = response.jsonPath().getList("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, returnConsumptions);
	}

	/**
	 * @Test10 This method retrieves and verifies the list of discharged patients.
	 * 
	 * @param endpoint - The API endpoint to which the GET request is sent.
	 * @param body     - Optional
	 *
	 * @return CustomResponse - The API response includes HTTP status code, status
	 *         message, and the list of discharged patients in the "Results" field,
	 *         containing details such as VisitCode, PatientVisitId, and PatientId.
	 */
	public CustomResponse getDischargedPatients(String endpoint, Object body) {
		// Set up the request
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		List<Map<String, Object>> dischargedPatients = response.jsonPath().getList("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, dischargedPatients);
	}

	/**
	 * @Test11 This method fetches film types available in the radiology department.
	 * 
	 * @param endpoint - The API endpoint to retrieve the list of radiology film
	 *                 types.
	 * @param body     - Optional request body to include additional parameters or
	 *                 filters for the API call.
	 * @description This method constructs a GET request with authorization and
	 *              content-type headers. If a body is provided, it is included in
	 *              the request; otherwise, the request is sent without a body. The
	 *              response is extracted and returned for further processing.
	 * @return CustomResponse - The response from the API containing details about
	 *         the available radiology film types.
	 */
	public CustomResponse getFilmTypesInRadiology(String endpoint, Object body) {
		// Set up the request
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		List<Map<String, Object>> results = response.jsonPath().getList("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}

	/**
	 * @Test12 This method retrieves requisitions based on order status and a
	 *         specified date range.
	 * 
	 * @param endpoint - The API endpoint to fetch requisitions filtered by order
	 *                 status and date range.
	 * @param body     - Optional request body to include additional filters or
	 *                 parameters for the API call.
	 * @description This method constructs a GET request with authorization and
	 *              content-type headers. If a body is provided, it is included in
	 *              the request; otherwise, the request is sent without a body. The
	 *              response is extracted and returned for further processing.
	 * @return CustomResponse - The response from the API containing requisitions
	 *         that match the specified criteria.
	 */
	public CustomResponse getRequisitionsByOrderStatusAndDateRange(String endpoint, Object body) {
		// Set up the request
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		List<Map<String, Object>> results = response.jsonPath().getList("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}

	/**
	 * @Test13 This method retrieves imaging reports based on status and a specified
	 *         date range.
	 * 
	 * @param endpoint - The API endpoint to fetch imaging reports with specific
	 *                 status and date range.
	 * @param body     - Optional request body to include in the API call; can
	 *                 contain filters or parameters.
	 * @description This method builds a GET request with authorization and
	 *              content-type headers. If a body is provided, it is included in
	 *              the request; otherwise, the request is sent without a body. The
	 *              response is extracted and returned for further processing.
	 * @return CustomResponse - The response from the API containing imaging reports
	 *         based on the specified criteria.
	 */
	public CustomResponse getImagingReportsWithStatusAndDateRange(String endpoint, Object body) {
		// Set up the request
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		List<Map<String, Object>> results = response.jsonPath().getList("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}

	/**
	 * @Test14 This method retrieves data for admitted patients.
	 * 
	 * @param endpoint - The API endpoint to fetch the admitted patient data.
	 * @param body     - Optional request body containing additional parameters for
	 *                 the API call.
	 * @description This method builds a GET request with authorization and
	 *              content-type headers. If a body is provided, it is included in
	 *              the request; otherwise, the request is sent without a body. The
	 *              response from the API is extracted and returned for further
	 *              processing.
	 * @return CustomResponse - The API response containing details of admitted
	 *         patients.
	 */
	public CustomResponse getAdmittedPatientData(String endpoint, String body) {
		// Set up the request
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		Map<String, Object> results = response.jsonPath().getMap("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}

	/**
	 * @Test15 This method retrieves provisional items for inpatients.
	 * 
	 * @param endpoint - The API endpoint to fetch inpatient provisional items.
	 * @param body     - Optional map containing additional parameters for the API
	 *                 call.
	 * @description This method constructs a GET request with authorization and
	 *              content-type headers. If a body is provided, it is included in
	 *              the request as JSON; otherwise, the request is sent without a
	 *              body. The response from the API is extracted and returned for
	 *              further processing.
	 * @return CustomResponse - The API response containing inpatient provisional
	 *         items.
	 */
	public CustomResponse getInPatientProvItems(String endpoint, Map<String, String> body) {
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		List<Map<String, Object>> results = response.jsonPath().getList("Results.BillItems");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}

	/**
	 * @Test16 This method retrieves a list of doctors based on the provided criteria.
	 * 
	 * @param endpoint - The API endpoint to fetch the doctors list.
	 * @param body     - Optional map containing parameters for filtering the
	 *                 doctors list.
	 * @description This method builds a GET request with the necessary
	 *              authorization and content-type headers. If a body is provided,
	 *              it is sent as a JSON payload; otherwise, the request is executed
	 *              without a body. The method extracts and returns the API response
	 *              containing the list of doctors.
	 * @return CustomResponse - The API response with the list of doctors.
	 */
	public CustomResponse getDoctorsList(String endpoint, Map<String, String> body) {
		RequestSpecification request = RestAssured.given().header("Authorization", AuthUtil.getAuthHeader())
				.header("Content-Type", "application/json");

		// Only add the body if it's not null
		if (body != null) {
			request.body(body);
		}

		// Execute the GET request and extract the response
		Response response = request.get(BASE_URL + endpoint).then().extract().response();

		// Extract required data from the response
		int statusCode = response.statusCode();
		String status = response.jsonPath().getString("Status");
		List<Map<String, Object>> results = response.jsonPath().getList("Results");

		// Return a CustomResponse object
		return new CustomResponse(response, statusCode, status, results);
	}
}