# Configuration File Help (English)
### [简体中文](配置文件帮助文档.md) | [English](ConfigurationFileHelp.md)
---
### setting.yml
```yml
#Language File
language: Language_CN.yml
#At Login
Login:
  #About Location
  Location:
    #Login World
    World: world
    #Login Coordinate - X
    X: 240.0
    #Login Coordinate - Y
    Y: 76.0
    #Login Coordinate - Z
    Z: 112.0
#Whether to allow movement before logging in
AllowMove: 'no'
#Whether to return to the Location position each time you log in
Return: 'yes'
#Should the player be returned to their previous location after logging in?
Back: 'yes'
#Use database SQLite/MySQL
SQL: SQLite
#MySQL Configuration
#MySQL will check if the target database exists when starting, and if it does not exist, it will automatically create the database
MySQL:
  #MySQL Address
  Address: localhost
  #MySQL Port
  Port: '3306'
  #MySQL Database Name
  Database: Data
  #MySQL Login Account
  User: root
  #MySQL Login Password
  Password: '123456'
#SQLite Configuration
#SQLite will check if the target database exists when starting, and if it does not exist, it will automatically create the database
SQLite:
  #SQLite Database Path
  #Fill in "this" to represent that the database path is located in the plugin folder, that is, the "ElysiumLogin" folder
  #If you need to fill in the path yourself, please use "/" as the separator.
  Path: this
  #SQLite Database Name
  Database: Data
```
---
## Language_XX.yml
```yml
#Message Prefix
prefix: '§7| §3服务器 §7| '
#Help Information
help:
  #Command Help Information
  command:
    #Command elysiumlogin: View plugin information
    elysiumlogin: §a /elysiumlogin §f- §e查看插件信息
    #Command elysiumlogin info: View plugin information
    elysiumlogin_info: §a /elysiumlogin info §f- §e查看插件信息
    #Command elysiumlogin help: View help information
    elysiumlogin_help: §a /elysiumlogin help §f- §e查看帮助信息
    #Command elysiumlogin reload: Reload configuration file
    elysiumlogin_reload: §a /elysiumlogin reload §f- §e重新加载配置文件
    #Command l <password>: Login
    l: §a /l <密码> §f- §e登入
    #Command login <password>: Login
    l: §a /login <密码> §f- §e登入
#About Login
login:
  #The player has already logged in.
  already: §c你已经登入了！
  #Wrong password
  Error: §c密码错误！
  #Player logs out
  exit: §a已退出登入
  #Player not login
  noLogin: §c你还未登入
  #Server error
  ServerError: §c登入失败，服务器内部错误，请联系管理员！
  #Login successful
  Successful: §a登入成功！
  #Login timed out
  TimeOut: §c登入超时
#Only players can execute this command (sent to the console)
onlyPlayer: §c只有玩家才能执行该命令！
#When player data is not in the database
playerNotFound: §c您还未注册，请先到官网注册！
#About reloading configuration files
reload:
  #Language File
  Language:
    #Language file reload failed
    Filed: §eLanguage §c配置文件重载失败
    #Language file reload successful
    Successful: §eLanguage §a配置文件重载成功
  #Settings File
  Setting:
    #Settings file reload failed
    Filed: §eSetting §c配置文件重载失败
    #Settings file reload successful
    Successful: §eSetting §a配置文件重载成功
#About SQLite Database
SQLite:
  #Database dependency not found
  ClassNotFound: §c未找到数据库依赖！
  #Database creation/connection failed
  CreateError: §c数据库创建/连接失败！
#When the value corresponding to "world" in the "setting.yml" file is invalid
WorldNotFound: §c未知的世界
#Official Website
web: '§e官网 §f: §awww.login.com'
```
