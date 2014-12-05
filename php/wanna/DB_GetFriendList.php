<?php
$response = array();
 
// include db connect class
require_once __DIR__ . '/include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();
require_once '/DB_CheckLogin.php';

if($sessionSuccess == 1){
	$userID=$_SESSION['userid'];
        $result = mysql_query("SELECT friend_one as friend FROM `friend` WHERE `friend_two` = $userID AND status ='1' UNION SELECT friend_two as friend FROM `friend` WHERE `friend_one` = $userID and status='1'");		
        if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
                        
			// looping through all results
			$response["friendList"] = array();
			while ($row = mysql_fetch_array($result)) {
                                $friendID = $row['friend'];
                                $resultForProfile = mysql_query("SELECT * FROM personprofile where userID = $friendID");
				$resultForProfile = mysql_fetch_array($resultForProfile);
				// temp user array
				$friendList = array();
				$friendList["profileID"] = $resultForProfile["profileID"];
				$friendList["nickName"] = $resultForProfile["nickName"];
				// push single event into final response array
				array_push($response["friendList"], $friendList);
			}
			
            // success
            $response["success"] = 1;
	    $response["message"] = "Get friend list";
            // echo no users JSON
            echo json_encode($response);
			}else{          
            // failed
            $response["success"] = 0;
	    $response["message"] = "No friend list exit";
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