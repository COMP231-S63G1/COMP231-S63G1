<?php
$response = array(); 
// include db connect class
require_once __DIR__ . '/include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();
if (isset($_POST["searchType"])) {	
$searchType = $_POST["searchType"];
	if ($searchType== "Name" && isset($_POST["searchName"])) {
		$searchName = $_POST["searchName"];
		$result = mysql_query("SELECT `profileID`, `nickName` FROM `personprofile` WHERE `nickName` LIKE '%$searchName%'");
		}
	elseif($searchType== "Filtration" && isset($_POST["searchStatus"]) && isset($_POST["searchMinAge"]) && isset($_POST["searchMaxAge"]) && isset($_POST["searchGender"])){
		$searchStatus = $_POST["searchStatus"];
		$searchGender = $_POST["searchGender"];
		$searchMaxAge = $_POST["searchMaxAge"];
		$searchMinAge = $_POST["searchMinAge"];		
		$result = mysql_query("SELECT `profileID`, `nickName` FROM `personprofile` WHERE `profileID` = (SELECT `profileID` FROM `users` WHERE `userID` = (SELECT DISTINCT(`userID`) FROM `eventjoinin` WHERE `eventID` =  (SELECT DISTINCT(`eventID`) FROM `event` WHERE `eventName` LIKE '%$searchStatus%' AND `gender` LIKE '$searchGender%' AND `age` BETWEEN $searchMinAge AND $searchMaxAge)))");	
		}else{
			// failed
			$response["success"] = 0;
			$response["message"] = "Pass data failed";
			echo json_encode($response);
			}
			if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
			// looping through all results
			$response["userList"] = array();
			while ($row = mysql_fetch_array($result)) {
				// temp user array
				$user = array();
				$user["profileID"] = $row["profileID"];
				$user["profileName"] = $row["nickName"];
				// push single group into final response array
				array_push($response["userList"], $user);
			}          
            // success
            $response["success"] = 1;
			$response["message"] = "Get user name";
            // echo no users JSON
            echo json_encode($response);
			}else{          
            // failed
            $response["success"] = 0;
			$response["message"] = "No user name";
            // echo no users JSON
            echo json_encode($response);
			}
	}else{          
            // failed
            $response["success"] = 0;
			$response["message"] = "Database conncetion failed";
            // echo no users JSON
            echo json_encode($response);
	}
}else{
	// failed
	$response["success"] = 0;
	$response["message"] = "Pass search type failed";
	echo json_encode($response);
}
?>