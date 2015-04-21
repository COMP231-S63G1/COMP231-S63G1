<?php
//request url
$url    = 'https://android.googleapis.com/gcm/send';
 
//your api key
$apiKey = 'AIzaSyAd6juDRu0ofqsdVO5RUzAMAa3MgBB7Hng';
 
//registration ids
$registrationIDs = array($gcm_regid);
 
//payload data
$data   = array('data' => $gcm_data) ;
 
$fields = array('registration_ids' => $registrationIDs,
                'data' => $data);
 
//http header
$headers = array('Authorization: key=' . $apiKey,
                 'Content-Type: application/json');
 
//curl connection
$ch = curl_init();
 
curl_setopt($ch, CURLOPT_URL, $url);
curl_setopt($ch, CURLOPT_POST, true);
curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true );
curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
 
$result = curl_exec($ch);
 
curl_close($ch);
 
echo $result;
?>