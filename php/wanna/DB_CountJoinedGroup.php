<?php
$response = array();
 
// include db connect class
require_once __DIR__ . '/include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();
require_once '/DB_CheckLogin.php';

if($sessionSuccess == 1){
	$userID=$_SESSION['userid'];
        $result = mysql_query("SELECT COUNT(`groupID`) FROM `groupjoinin` WHERE `userID` = $userID");	
	if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
			// looping through all results
			while($row=mysql_fetch_array($result))  {
				$countGroup = $row['COUNT(`groupID`)'];
			}
			$response["countGroup"] = $countGroup;
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