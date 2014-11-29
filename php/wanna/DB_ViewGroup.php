<?php
 
/*
 * Following code will get single group detail
 * A profile is identified by group id (groupID)
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();

require_once '/DB_CheckLogin.php';
if($sessionSuccess == 1){
	$userID=$_SESSION["userid"];
	if (isset($_POST["groupID"])) {
	// get group detail group table
   $groupID = $_POST['groupID'];
    $result = mysql_query("SELECT * FROM `group` WHERE `groupID`=$groupID");
	
	if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
 
            $result = mysql_fetch_array($result);
 
            $groupDetail= array();
            $groupDetail["groupID"] = $result["groupID"];
            $groupDetail["groupName"] = $result["groupName"];
            $groupDetail["groupType"] = $result["groupType"];
            $groupDetail["groupDescription"] = $result["groupDescription"];           
            // success
            $response["success"] = 1; 
            // user node
            $response["groupDetail"] = array(); 
            array_push($response["groupDetail"], $groupDetail);
			$response["message"] = "Get group success"; 
            // echoing JSON response
            echo json_encode($response);
        } else {
            // no group found
            $response["success"] = 0;
			$response["message"] = "Get group failed"; 
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no profile found
        $response["success"] = 0;
		$response["message"] = "Database connecton failed";
 
        // echo no users JSON
        echo json_encode($response);
    }
}
else{
	// failed
	$response["success"] = 0;
	$response["message"] = "Pass data failed";
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