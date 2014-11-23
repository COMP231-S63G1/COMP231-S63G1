<?php
$response = array();
 
// include db connect class
require_once __DIR__ . '/include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();
if (isset($_POST["searchType"])) {
$searchType = $_POST["searchType"];
if($searchType == "Name" && isset($_POST["searchGroupName"])){	
$searchGroupName = $_POST["searchGroupName"];
$result = mysql_query("SELECT `groupID`, `groupName`, `groupPrivacy` FROM `group` where `groupName` LIKE '%$searchGroupName%'");
}
else if($searchType == "Category" && isset($_POST["searchGroupCategory"])){
$searchGroupCategory = $_POST["searchGroupCategory"];	
$result = mysql_query("SELECT `groupID`, `groupName`, `groupPrivacy` FROM `group` where `groupType`='$searchGroupCategory'");
}
else{
	// failed
	$response["success"] = 0;
	$response["message"] = "Pass data failed";
	echo json_encode($response);
}	
	if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
			// looping through all results
			$response["groupList"] = array();
			while ($row = mysql_fetch_array($result)) {
				// temp user array
				$group = array();
				$group["groupPrivacy"] = $row["groupPrivacy"];
				$group["groupID"] = $row["groupID"];
				$group["groupName"] = $row["groupName"];
				// push single group into final response array
				array_push($response["groupList"], $group);
			}          
            // success
            $response["success"] = 1;
			$response["message"] = "Get group name";
            // echo no users JSON
            echo json_encode($response);
			}else{          
            // failed
            $response["success"] = 0;
			$response["message"] = "No group name";
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
}
else{
	// failed
	$response["success"] = 0;
	$response["message"] = "Pass group type failed";
	echo json_encode($response);
}
?>