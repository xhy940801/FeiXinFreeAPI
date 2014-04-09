<?php
class FeixinFreeAPI
{
	private $hostConfig;
	private $systemConfig;
	private $stream;
	private $error;
	private $errstr;
	private $userConfig;

	public function __construct()
	{
		$this->hostConfig = include('conf/host_config.php');
		$this->systemConfig = include('conf/system_config.php');
		$this->userConfig = include('conf/user_config.php');
		$this->stream = fsockopen($this->hostConfig['hostUrl'], $this->hostConfig['port'], $this->errno, $this->errstr, $this->hostConfig['timeout']);
	}

	public function login($verificationCode)
	{
		$checkStreamRes = $this->checkStream();
		if(is_array($checkStreamRes))
			return $checkStreamRes;
		else
		{
			fputs($this->stream, $this->userConfig['token']."\n");
			fputs($this->stream, "login\n");
			fputs($this->stream, $this->userConfig['username']."\n");
			fputs($this->stream, $this->userConfig['password']."\n");
			fputs($this->stream, $verificationCode."\n");
			fputs($this->stream, $_COOKIE[$this->systemConfig['verificationCookieKeyName']]."\n");
			$returnCode = fgets($this->stream);
			$returnCode = intval($returnCode);
			return $this->decodeReturnCode($returnCode);
		}
	}

	public function sendMsg($msg, $receiver)
	{
		$checkStreamRes = $this->checkStream();
		if(is_array($checkStreamRes))
			return $checkStreamRes;
		else
		{
			fputs($this->stream, $this->userConfig['token']."\n");
			fputs($this->stream, "send_msg\n");
			fputs($this->stream, $receiver."\n");
			fputs($this->stream, mb_strlen($msg, $this->systemConfig['msgCharset'])."\n");
			fputs($this->stream, $msg);

			$returnCode = fgets($this->stream);
			$returnCode = intval($returnCode);
			return $this->decodeReturnCode($returnCode);
		}
	}

	private function checkStream()
	{
		if(!$this->stream)
		{
			return array(
				'returnCode' => '1',
				'returnMsg' => 'Failed to connect to server',
				'serverMsg' => ''
				);
		}
		else
			return 1;
	}

	private function decodeReturnCode($returnCode)
	{
		switch ($returnCode)
		{
		case 1:
			return array(
				'returnCode' => '0',
				'returnMsg' => 'Operator Success',
				'serverMsg' => ''
				);
			break;
		case 0:
			return array(
				'returnCode' => '2',
				'returnMsg' => 'Decode message from server error',
				'serverMsg' => ''
				);
		case 2:
			return array(
				'returnCode' => '3',
				'returnMsg' => 'Request timeout',
				'serverMsg' => fgets($this->stream)
				);
		case 3:
			return array(
				'returnCode' => '4',
				'returnMsg' => 'Request fail',
				'serverMsg' => fgets($this->stream)
				);
		case 4:
			return array(
				'returnCode' => '5',
				'returnMsg' => 'Request error',
				'serverMsg' => fgets($this->stream)
				);
		case 5:
			return array(
				'returnCode' => '6',
				'returnMsg' => 'Not login',
				'serverMsg' => fgets($this->stream)
				);
		default:
			return array(
				'returnCode' => '-1',
				'returnMsg' => 'SYSTEM ERROR!',
				'serverMsg' => ''
				);
			break;
		}
	}
}