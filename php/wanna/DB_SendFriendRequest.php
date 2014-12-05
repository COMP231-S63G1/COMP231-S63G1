<?php
 
/*
 * Following code will add a row in friend table if there is no corespond row exit
 * first check whether the coposite primary key exit, if exit and status is 0 add friend autometically
 * if exit and status is 1 respound to use the person user request to be a friend is already in the friend list
 * if not exit than insert a row in the friend table and return message the friend request is already sent 
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();

require_once '/DB_CheckLogin.php';

if($sessionSuccess == 1){
	$userID=$_SESSION['userid'];
	// check for post data

	if (isset($_POST["profileID"])) {

            $profileID = $_POST["profileID"];
            $result = mysql_query("SELECT `userid` FROM personprofile WHERE profileID = $profileID");
            $result = mysql_fetch_array($result);
            $friendID=$result["userid"];

	$result = mysql_query("SELECT * FROM `friend` where (`friend_one` = $userID AND `friend_two` = $friendID ) OR ( `friend_one` = $friendID AND `friend_two` = $userID )");
        if (mysql_num_rows($result) > 0) {
            $result = mysql_fetch_array($result);
            if($result["status"]==0)
            $response["message"]="You have already sent the request to this person";
            if($result["status"]==1)
            $response["message"]="You already be friend with this person";
	    $response["success"] = 0;
	    echo json_encode($response);
        }else{
        //insert the friend  into the child table
		$result = mysql_query("INSERT INTO `friend` (`friend_one`, `friend_two`,`status`) VALUES ($userID, $friendID,'0')");
		if ($result) {	
		// successfully inserted into database	
        	$response["success"] = 1;
			$response["message"] = "Your request have already be sent!";
 
        	// echoing JSON response
        	echo json_encode($response);
		}
		else{
        	// inserted into database failed
        	$response["success"] = 0;
		$response["message"] = "Insert into database fail please check connection!";
 
        	// echoing JSON response
        	echo json_encode($response);
		}
        }
    
}
	else{
        // pass data failed
        $response["success"] = 0;
	$response["message"] = "Pass data failed";
 
        // echoing JSON response
        echo json_encode($response);
	}
}
else{
		// failed
		$response["success"] = 0;
		$response["message"] = $sessionMessage;
		// echoing JSON response
		echo json_encode($response);	
}
?>