<?php
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/include/DB_Connect.php';
$db = new DB_Connect();
require_once __DIR__ . '/DB_CheckLogin.php';
if($sessionSuccess == 1){
	$userid = $_SESSION['userid'];
	if($_SESSION['userType'] == "Person"){
		if (isset($_POST["latitude"])&& isset($_POST["longitude"])) {
			$latitude = number_format($_POST["latitude"], 7, '.', '');
			$longitude = number_format($_POST["longitude"], 7, '.', '');
			$result = mysql_query("UPDATE `wanna`.`users` SET `latitude` = '$latitude', `longitude` = '$longitude' WHERE `userid` = '$userid'");
			$error = mysql_error(); 
			if($result){
				// success
				$response["success"] = 1;
				$response["message"] = $latitude;
				$response["userid"] = $_SESSION['userid'];
				$response["nickName"] = $_SESSION['nickName'];
				// echoing JSON response
				echo json_encode($response);
			}else{
				// required field is missing
				$response["success"] = 2;
				$response["message"] = "Upload location failed";
				// echoing JSON response
				echo json_encode($response);				
			}
			}else{
				// required field is missing
				$response["success"] = 2;
				$response["message"] = "Pass data failed";
				// echoing JSON response
				echo json_encode($response);
				}
		}else{ // $_SESSION['userType'] == "Prganization"		
		// success
		$response["success"] = 1;
		$response["message"] = $sessionMessage;
		$response["userid"] = $_SESSION['userid'];
		$response["nickName"] = $_SESSION['nickName'];
		// echoing JSON response
		echo json_encode($response);
		}	
		if($_SESSION['userType'] == "Organization"){
		if (isset($_POST["latitude"])&& isset($_POST["longitude"])) {
			$latitude = number_format($_POST["latitude"], 7, '.', '');
			$longitude = number_format($_POST["longitude"], 7, '.', '');
			$result = mysql_query("UPDATE `wanna`.`users` SET `latitude` = '$latitude', `longitude` = '$longitude' WHERE `userid` = '$userid'");
			$error = mysql_error(); 
			if($result){
				// success
				$response["success"] = 1;
				$response["message"] = $latitude;
				$response["userid"] = $_SESSION['userid'];
				// echoing JSON response
				echo json_encode($response);
			}else{
				// required field is missing
				$response["success"] = 2;
				$response["message"] = "Upload location failed";
				// echoing JSON response
				echo json_encode($response);				
			}
			}else{
				// required field is missing
				$response["success"] = 2;
				$response["message"] = "Pass data failed";
				// echoing JSON response
				echo json_encode($response);
				}
		}else{ // $_SESSION['userType'] == "Prganization"		
		// success
		$response["success"] = 1;
		$response["message"] = $sessionMessage;
		$response["userid"] = $_SESSION['userid'];
		// echoing JSON response
		echo json_encode($response);
		}	
}else{
		// failed
		$response["success"] = 0;
		$response["message"] = $sessionMessage;
		// echoing JSON response
		echo json_encode($response);	
} 
?>