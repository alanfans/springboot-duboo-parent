from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.action_chains import ActionChains
import time
class Am_auto():
	account = {
		'email' : 'admin@vps6.win',
		'passwd' : ':*)?8A2f4Fck',
	}
	em_botton = {
		'earn' : 'earn_points.php',
		'mem_pts': 'mem_pts',
		'ads': (By.XPATH, ".//a[contains(@onclick, 'openSite')]"),
	}
	em_url = {
		'chrome' : 'http://chromedriver.storage.googleapis.com/index.html',
		'login' : 'https://www.alexamaster.net/sec/login.php',
		'loginConfirm' : 'https://www.alexamaster.net/sec/loginconfirm.php',
		'a' : 'https://www.alexamaster.net/a',
		'profile' : 'https://www.alexamaster.net/a/my_profile.php',
		'earn' : 'https://www.alexamaster.net/a/earn_points.php'
	}

	def __init__(self,browerType):
		if 'chrome' == browerType:
			self.brower = webdriver.Chrome('E:\Program Files\selenium\chromedriver.exe')
		else:
			self.brower = webdriver.Firefox(executable_path='E:\Program Files\selenium\geckodriver.exe')

	def login(self):
		self.brower.get(self.em_url.get('login'))
		# print("firefox:"+fbrower.page_source)
		try:
			_input = self.brower.execute_script("return $('.form').find('input')")
			# email
			_input[0].send_keys(self.account.get('email'))
			# pwd
			_input[1].send_keys(self.account.get('passwd'))
			_input[2].click()
			time.sleep(3)
			self.brower.get(self.em_url.get('loginConfirm'))
			self.brower.get(self.em_url.get('a'))
			time.sleep(3)
			mem_pts = self.brower.find_element_by_id(self.em_botton.get('mem_pts'))
			print('mem_pts:'+mem_pts.text)
		except BaseException as identifier:
			self.login()
		
	def visite_earn_page_scroll(self):
		try:
	   		# not find earn_pgae
			# button_earn = brower.find_element(By.LINK_TEXT,self.em_url.get("earn"))
			# button_earn.click()
			# brower.get(self.em_url.get('earn'))5,261
			self.brower.get(self.em_url.get('earn'))
			js = 'document.getElementsByClassName("main-panel")[0].scrollTop'
			height = self.brower.execute_script("return "+js)
			self.brower.execute_script(js+"="+str(height+10000))
			print('height:'+str(height+10000))
		except BaseException as identifier:
	   		# self.brower.refresh()
	   		self.visite_earn_page_scroll()
		
	def checkGV(self):
		time.sleep(6)
		gvs_div = self.brower.execute_script("return $('.timeline-panel')")
		hasGV = 0
		for gv in gvs_div:
			if '5 Good Votes from' in gv.text:
				try:
					ads = gv.find_element(*self.em_botton.get('ads'))
					self.getGV(ads)
					hasGV = 1
				except BaseException as identifier:
					print("error")
				
		if hasGV == 0:
			self.visite_earn_page_scroll()
			self.checkGV()

	def getGV(self,ads):
		ads.click()
		time.sleep(21)
		# switch_tab
		windows = self.brower.window_handles
		self.brower.switch_to.window(windows[1])
		self.brower.close()
		self.brower.switch_to.window(windows[0])
		goodconfirm = self.brower.find_element_by_class_name("swal2-confirm")
		goodconfirm.click()
		goodconfirm = self.brower.find_element_by_class_name("swal2-confirm")
		goodconfirm.click()
		time.sleep(3)
		self.checkGV()

test = Am_auto('chrome')
test.login()
test.visite_earn_page_scroll()
test.checkGV()