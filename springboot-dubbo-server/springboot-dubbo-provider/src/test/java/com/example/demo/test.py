from selenium import webdriver
from selenium.webdriver.common.by import By
import time

class Am_static():
	browerType = "chrome"
	account = {
		'email' : '123@6vip.ml',
		'passwd' : ':*)?8A2f4Fck',
	}
	em_botton = {
		'earn' : 'earn_points.php',
		'mem_pts': 'mem_pts'
	}
	em_url = {
		'chrome' : 'http://chromedriver.storage.googleapis.com/index.html',
		'login' : 'https://www.alexamaster.net/sec/login.php',
		'loginConfirm' : 'https://www.alexamaster.net/sec/loginconfirm.php',
		'a' : 'https://www.alexamaster.net/a',
		'profile' : 'https://www.alexamaster.net/a/my_profile.php',
		'earn' : 'https://www.alexamaster.net/a/earn_points.php'
	}
	@staticmethod
	def getBrower(self):
		if 'chrome' == self.browerType:
			self.brower = webdriver.Chrome('/Users/hylc/Downloads/chromedriver')
		else:
			self.brower = webdriver.Firefox(executable_path='/Users/hylc/Downloads/geckodriver')
			
	def login(self):
		brower.get(self.em_url.get('login'))
		# print("firefox:"+fbrower.page_source)
		_input = brower.execute_script("return $('.form').find('input')")
		# email
		_input[0].send_keys(self.account.get('email'))
		# pwd
		_input[1].send_keys(self.account.get('passwd'))
		_input[2].click()
		time.sleep(3)
		brower.get(self.em_url.get('loginConfirm'))
		brower.get(self.em_url.get('a'))
		time.sleep(3)
		mem_pts = brower.find_element_by_id(self.em_botton.get('mem_pts'))
		print('mem_pts:'+mem_pts.text)
		
	def visite_earn_page_scroll(self):
    	# not find earn_pgae
		# button_earn = brower.find_element(By.LINK_TEXT,self.em_url.get("earn"))
		# button_earn.click()
		# brower.get(self.em_url.get('earn'))
		brower.get(self.em_url.get('earn'))



test = Am_static()
test.login()
test.visite_earn_page()