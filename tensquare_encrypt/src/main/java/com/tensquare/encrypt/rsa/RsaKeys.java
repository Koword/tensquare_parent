package com.tensquare.encrypt.rsa;

/**
 * rsa加解密用的公钥和私钥
 * @author Administrator
 *
 */
public class RsaKeys {

	//生成秘钥对的方法可以参考这篇帖子
	//https://www.cnblogs.com/yucy/p/8962823.html

//	//服务器公钥
//	private static final String serverPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6Dw9nwjBmDD/Ca1QnRGy"
//											 + "GjtLbF4CX2EGGS7iqwPToV2UUtTDDemq69P8E+WJ4n5W7Iln3pgK+32y19B4oT5q"
//											 + "iUwXbbEaAXPPZFmT5svPH6XxiQgsiaeZtwQjY61qDga6UH2mYGp0GbrP3i9TjPNt"
//											 + "IeSwUSaH2YZfwNgFWqj+y/0jjl8DUsN2tIFVSNpNTZNQ/VX4Dln0Z5DBPK1mSskd"
//											 + "N6uPUj9Ga/IKnwUIv+wL1VWjLNlUjcEHsUE+YE2FN03VnWDJ/VHs7UdHha4d/nUH"
//											 + "rZrJsKkauqnwJsYbijQU+a0HubwXB7BYMlKovikwNpdMS3+lBzjS5KIu6mRv1GoE"
//											 + "vQIDAQAB";
//
//	//服务器私钥(经过pkcs8格式处理)
//	private static final String serverPrvKeyPkcs8 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDoPD2fCMGYMP8J"
//				 								 + "rVCdEbIaO0tsXgJfYQYZLuKrA9OhXZRS1MMN6arr0/wT5YniflbsiWfemAr7fbLX"
//				 								 + "0HihPmqJTBdtsRoBc89kWZPmy88fpfGJCCyJp5m3BCNjrWoOBrpQfaZganQZus/e"
//				 								 + "L1OM820h5LBRJofZhl/A2AVaqP7L/SOOXwNSw3a0gVVI2k1Nk1D9VfgOWfRnkME8"
//				 								 + "rWZKyR03q49SP0Zr8gqfBQi/7AvVVaMs2VSNwQexQT5gTYU3TdWdYMn9UeztR0eF"
//				 								 + "rh3+dQetmsmwqRq6qfAmxhuKNBT5rQe5vBcHsFgyUqi+KTA2l0xLf6UHONLkoi7q"
//				 								 + "ZG/UagS9AgMBAAECggEBANP72QvIBF8Vqld8+q7FLlu/cDN1BJlniReHsqQEFDOh"
//				 								 + "pfiN+ZZDix9FGz5WMiyqwlGbg1KuWqgBrzRMOTCGNt0oteIM3P4iZlblZZoww9nR"
//				 								 + "sc4xxeXJNQjYIC2mZ75x6bP7Xdl4ko3B9miLrqpksWNUypTopOysOc9f4FNHG326"
//				 								 + "0EMazVaXRCAIapTlcUpcwuRB1HT4N6iKL5Mzk3bzafLxfxbGCgTYiRQNeRyhXOnD"
//				 								 + "eJox64b5QkFjKn2G66B5RFZIQ+V+rOGsQElAMbW95jl0VoxUs6p5aNEe6jTgRzAT"
//				 								 + "kqM2v8As0GWi6yogQlsnR0WBn1ztggXTghQs2iDZ0YkCgYEA/LzC5Q8T15K2bM/N"
//				 								 + "K3ghIDBclB++Lw/xK1eONTXN+pBBqVQETtF3wxy6PiLV6PpJT/JIP27Q9VbtM9UF"
//				 								 + "3lepW6Z03VLqEVZo0fdVVyp8oHqv3I8Vo4JFPBDVxFiezygca/drtGMoce0wLWqu"
//				 								 + "bXlUmQlj+PTbXJMz4VTXuPl1cesCgYEA6zu5k1DsfPolcr3y7K9XpzkwBrT/L7LE"
//				 								 + "EiUGYIvgAkiIta2NDO/BIPdsq6OfkMdycAwkWFiGrJ7/VgU+hffIZwjZesr4HQuC"
//				 								 + "0APsqtUrk2yx+f33ZbrS39gcm/STDkVepeo1dsk2DMp7iCaxttYtMuqz3BNEwfRS"
//				 								 + "kIyKujP5kfcCgYEA1N2vUPm3/pNFLrR+26PcUp4o+2EY785/k7+0uMBOckFZ7GIl"
//				 								 + "FrV6J01k17zDaeyUHs+zZinRuTGzqzo6LSCsNdMnDtos5tleg6nLqRTRzuBGin/A"
//				 								 + "++xWn9aWFT+G0ne4KH9FqbLyd7IMJ9R4gR/1zseH+kFRGNGqmpi48MS61G0CgYBc"
//				 								 + "PBniwotH4cmHOSWkWohTAGBtcNDSghTRTIU4m//kxU4ddoRk+ylN5NZOYqTxXtLn"
//				 								 + "Tkt9/JAp5VoW/41peCOzCsxDkoxAzz+mkrNctKMWdjs+268Cy4Nd09475GU45khb"
//				 								 + "Y/88qV6xGz/evdVW7JniahbGByQhrMwm84R9yF1mNwKBgCIJZOFp9xV2997IY83S"
//				 								 + "habB/YSFbfZyojV+VFBRl4uc6OCpXdtSYzmsaRcMjN6Ikn7Mb9zgRHR8mPmtbVfj"
//				 								 + "B8W6V1H2KOPfn/LAM7Z0qw0MW4jimBhfhn4HY30AQ6GeImb2OqOuh3RBbeuuD+7m"
//				 								 + "LpFZC9zGggf9RK3PfqKeq30q";

	//服务器公钥
	private static final String serverPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvjnl+X9glIicU+/ETOOV\n"
		+ "4IteYxHgnCrfbZFmDobpcz40CiDQxLLemJjLkGDxdtEvyQNvkyNBtlJxFLRbIH21\n"
		+ "xbl/1T3ikf5upZ9S4LYA0HADIVsY5USR6mt7jA6fb3aaE9cUUMQ0KS1JPphd4Ldx\n"
		+ "yqExE4DMjnoPnHaBMDp71j0mDe8OEj8Hi+X77wOco/892qYpsoGuL3bxp6TFu5BM\n"
		+ "XJrO1tRco8xa5feF6Xsh8OOANc2hKxfANv2qBfUjPQ6m3X7FDOOPc3iY3ZIzIdYL\n"
		+ "BAPqFBjMETXKEXWaW7usJEFwisP6lhYMkpIRxVwRIpBe1LWPhhYGOkKTxl8dFcHP\n"
		+ "IQIDAQAB";

	//服务器私钥(经过pkcs8格式处理)
	private static final String serverPrvKeyPkcs8 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC+OeX5f2CUiJxT\n"
		+ "78RM45Xgi15jEeCcKt9tkWYOhulzPjQKINDEst6YmMuQYPF20S/JA2+TI0G2UnEU\n"
		+ "tFsgfbXFuX/VPeKR/m6ln1LgtgDQcAMhWxjlRJHqa3uMDp9vdpoT1xRQxDQpLUk+\n"
		+ "mF3gt3HKoTETgMyOeg+cdoEwOnvWPSYN7w4SPweL5fvvA5yj/z3apimyga4vdvGn\n"
		+ "pMW7kExcms7W1FyjzFrl94XpeyHw44A1zaErF8A2/aoF9SM9DqbdfsUM449zeJjd\n"
		+ "kjMh1gsEA+oUGMwRNcoRdZpbu6wkQXCKw/qWFgySkhHFXBEikF7UtY+GFgY6QpPG\n"
		+ "Xx0Vwc8hAgMBAAECggEBAI4Jn12PrebappfKFTIwUU4L4pto0uTo4lHm2mSIejIR\n"
		+ "xTu2ywvEX49jr8QLjuDgS6T1is876SohHsyN6uk01JBh9UCdZ9sJN0c10uV1IOCC\n"
		+ "cuPqKImwjJ+UR/kJRiUtUtjzpPi1uBjy9pjlnltXvoDFoWZlqtfL7nIuzkHbuXH6\n"
		+ "hnZSz+q4bGD3JMHk9GoykGRpjEK0ze8qeYlwR3iTkVp1QB/BCFWjFfRk4E9HGrv4\n"
		+ "Vpphr9ANg6UND6cm7bXRiHdrJVhU2iWVN73E3ePiRYAKc+tq+rXZn9lpEIzmIPkP\n"
		+ "rysluio9scja8bAMFWFPyYvse7skhoycIuDVBSXqrNECgYEA67Exr31JyJ2qx1pb\n"
		+ "o1C36148o2EnRZFHdU2oCLplFPthPi8ao/j2GGlHvFQuUugzCvGyVIJ0ycQ1Bo8N\n"
		+ "V73Zf4FmaDnWXjx36oL5nm72O2ke6qQeWUlBXaDqS6HSV/QJPP2ld9FejldVckJL\n"
		+ "lK6+PJ/XJAXYslvKP0N6f6eeiqUCgYEAzp3VPT06+Ztc2aR+QcOc+oJYT0wpyiXJ\n"
		+ "iC96RavStMWTgt7Obp+XqEcXcHWzNWr4xjv9vaiymIU0hHOR6p64cgiOdp475rOK\n"
		+ "SBsNSQ22Qsyr/GNfAEFpP4sWwbfs2FVRQORr/JUZ3WC0qnYeKKtEk72SNyrsj6ne\n"
		+ "oiavM8F0Vc0CgYAIY+Vt5trijmJO+HN8q9nwQgmMk1/PhS1zCpGpxeDlT1GV4h1j\n"
		+ "qvAvzEemd6SyhNuUTyhXA9HegOKWWi9SaUq91EBuA2kTL7nhGkqCjlarpMFZ/v+p\n"
		+ "lAcEqz7L9HXFdcWCa1Eho81m6cr750pDf7j79YC2IpMlPklD2UYaF0B4lQKBgHZA\n"
		+ "5VvQYKGdpw211MFE+OJzmaGBAOX+rJKyQbQAfG8UKbIcXV/5HW6liafG5ZOsLsge\n"
		+ "a8KR++3JR16LWrdCsmQpu4fuVnBaz8knGnp2S5t90u133laiGV/R9mtXHkpOwlt2\n"
		+ "oMuGdrA6egyZ3/b81xoLxNSoT46F9bMumtXvYer9AoGBAN4eYYkGWyC30t6HRRqA\n"
		+ "WhJ1boEl2YhPEFdYjSoErs4ExcFMwYRojM4HpC6cenRstLBFuOTQlzwd8UH7hrF8\n"
		+ "1jTnjbcVozuAI6yV+9nqRwsqAwzPcW/sB5v41679oirXwKvNtuBZ9DmFwXPFhVCD\n"
		+ "a90MVSxnUsxKUZkhQRK1nEao";

	public static String getServerPubKey() {
		return serverPubKey;
	}

	public static String getServerPrvKeyPkcs8() {
		return serverPrvKeyPkcs8;
	}
	
}
