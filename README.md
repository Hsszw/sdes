**sdes**  
**第一关测试**  
**1.1GUI界面设计：**  
使用java+Swing的技术设计和制作S-DES算法加解密设计GUI界面，具有输入明文、密钥、输出等框，并且可以选择加密、解密、以及ascii格式或者二进制格式。  
![image](https://github.com/Hsszw/sdes/assets/147220550/9ade5bae-57f9-41f5-a0ba-c9052b56b20f)  
**1.2 使用测试用例进行测试：**  
测试明文：11011010  
密钥：1011010110  
测试密文：101100  
加密测试结果为：   
![image](https://github.com/Hsszw/sdes/assets/147220550/43bc4798-09b9-4d52-8c5d-715c54bd8f6e)  
解密测试：  
![image](https://github.com/Hsszw/sdes/assets/147220550/5517617b-e5d8-4b7e-b8bf-83fba857671d)  
通过以上测试显示加解密对应的明、密文正确。  
**第二关交叉测试：**  
参考其他小组的  
明文：10101010  
密钥：1111100000  
密文：00011011  
他们的结果是这样：  
![image](https://github.com/Hsszw/sdes/assets/147220550/ddc06a60-e358-45a3-b42c-f95017e5ae34)  
我们的结果是这样：  
![image](https://github.com/Hsszw/sdes/assets/147220550/80538d0a-de98-47bf-8782-1046e57ea6e0)  
**第三关：拓展功能**  
在交互页面我们可以选择ASCII码  
加密：  
![image](https://github.com/Hsszw/sdes/assets/147220550/8b308a8c-8e34-49dd-ab8b-6911cd3bb6f9)  
在通过选择ASCII码解密我们可以得到：  
![image](https://github.com/Hsszw/sdes/assets/147220550/8c5b6519-736c-4e89-b58f-9e443b4b03f2)  
**第四关：暴力破解**  
对于已经找到的明文和密文对，我们使用多线程的暴力破解方式：  
明文：10101010  
密文：11000001  
![image](https://github.com/Hsszw/sdes/assets/147220550/96d53dbb-00d9-4f6b-8707-1c87bfe5141d)  
我们采用多线程的破解方式寻找该明、密文对的正确密钥：  

![image](https://github.com/Hsszw/sdes/assets/147220550/674e6466-1385-4521-9936-4b51f2699ddb)  
通过暴力破解的方式得出，该明、密文对的密钥有两个：  
![image](https://github.com/Hsszw/sdes/assets/147220550/b59cf0c7-e65e-44fb-852d-692f5fe63cd6)  
密钥：0110000110  
密钥：1110000010  
时间：18ms  
而经过使用加密得出结果对比  
![image](https://github.com/Hsszw/sdes/assets/147220550/64066422-298c-444b-972c-c17b05b31ab6)  
![image](https://github.com/Hsszw/sdes/assets/147220550/51cfd476-f060-46d5-b654-6b510fa90e1a)  
经过匹配测试我们发现，这两个密钥确实为该明、密文对的正确密钥，说明该暴力破解成功。  
**第五关：封闭测试**  














