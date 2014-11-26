<?php
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['userType']) && isset($_POST['userName']) && isset($_POST['email'])&& isset($_POST['password'])) {
	$userType = $_POST['userType'];
	$userName = $_POST['userName'];
	$email = $_POST['email'];
	$password = $_POST['password'];
	
	// include db connect class
    require_once __DIR__ . '/include/DB_Connect.php';
 
    // connecting to db
    $db = new DB_Connect();
	
	// mysql check email existing
    $checkResult = mysql_query("SELECT userid FROM users WHERE email='$email'");
	if (!empty($checkResult)) {
        // check for empty result
        if (mysql_num_rows($checkResult) > 0) {
			//email has been registed
            $response["success"] = 0;
			$response["message"] = "email has been registed";  
            // echoing JSON response
            echo json_encode($response);
        } else {
			// mysql inserting a new row
			$insertResult = mysql_query("INSERT INTO `users` (`userType`, `userName`, `email`, `password`) VALUES('$userType', '$userName', '$email', PASSWORD('$password'))");
			// check if row inserted or not
			if ($insertResult) {
				//insert succeed
				//get new userid
				$getResult = mysql_query("SELECT `userid` FROM `users` WHERE `email` = '$email'");
				$getResult = mysql_fetch_array($getResult);
				$response["success"] = 1; 
				$response["userid"] = $getResult["userid"]; 
				$response["message"] = "Register succeed.";
				// echoing JSON response
				echo json_encode($response);				
			}else {
				// failed to insert row
				$response["success"] = 0;
				$response["message"] = "Register failed."; 
				// echoing JSON response
				echo json_encode($response);
				}
			
            $response["success"] = 1;
			$response["message"] = "register";  
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        $response["success"] = 0;
		$response["message"] = "Connection error"; 
        // echo no users JSON
        echo json_encode($response);
    }
}else
{
    // required field is missing
    $response["success"] = 0;
	$response["message"] = "Required field is missing.";
    // echoing JSON response
    echo json_encode($response);
}
?>