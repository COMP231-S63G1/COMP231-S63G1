<?php
$response = array();
 
// include db connect class
require_once __DIR__ . '/include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();
require_once '/DB_CheckLogin.php';

if($sessionSuccess == 1){
	$userID=$_SESSION['userid'];
	$profileID = $_SESSION['profileid'];

        $result = mysql_query("SELECT groupID FROM groupJOININ WHERE profileID = $profileID");	
	if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
			// looping through all results
			$response["groupList"] = array();
			while ($row = mysql_fetch_array($result)) {
                                $groupID = $row["groupID"];
                                $resultForGroupName = mysql_query("SELECT `groupID` ,`groupName` FROM `group` where `groupid` = $groupID");
				if (!empty($resultForGroupName)) {
        			// check for empty result
     				   if (mysql_num_rows($resultForGroupName) > 0) {
						$resultForGroupName = mysql_fetch_array($resultForGroupName); 
						// temp user array
					        $group = array();
						$group["groupID"] = $resultForGroupName["groupID"];
						$group["groupName"] = $resultForGroupName["groupName"];
						// push single group into final response array
				                array_push($response["groupList"], $group);
				   }
                                }
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
	$response["message"] = "Session out of time";
	// echo no users JSON
	echo json_encode($response);
}
?>