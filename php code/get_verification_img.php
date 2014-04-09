<?php
$hostConfig = include('conf/host_config.php');
$systemConfig = include('conf/system_config.php');
$userConfig = include('conf/user_config.php');

$file = fsockopen($hostConfig['hostUrl'], $hostConfig['port'], $errno, $errstr, $hostConfig['timeout']);

if($file)
{
	header("Content-Type: image/jpg");
	fputs($file, $userConfig['token']."\n");
	fputs($file, "get_v_img\n");
	$returnCode = fgets($file);
	$returnCode = intval($returnCode);
	if($returnCode == 1)
	{
		$ccp = fgets($file);
		$ccp = preg_replace('/^\\s|\\s$/', '', $ccp);
		setcookie($systemConfig['verificationCookieKeyName'], $ccp, time()+$systemConfig['cookieValidTime'], $systemConfig['AppRootPath']);
		$imgLength = fgets($file);
		$imgLength = intval($imgLength);
		$img = stream_get_contents($file, $imgLength);
		echo $img;
	}
}