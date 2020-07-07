MySql数据库

    - User： 用户表

   1. String userId  用户的ID 目前用自动生成的uuid
   2. String account 外部用户的账号
   3. String passWord 外部用户的密码
   4. String tel      手机号
   5. String name      花名
   6. String avatar    头像
   7. Date createTime  创建时间
   8. Date updateTime  更新时间
   9. String currentCountry   当前国家  登录时候指定，用于区分用户国家，方便考勤根据不同时区统计
   10. String currentTeamId   当前团队，初始化云信账号时设置，为了让用户少一个接口调用，故在选择团队登录不同云信调接口时一起上传，目前客户端
                        默认只是单团队，所以客户端固定传风先生团队的id
   11. int countryCode
    
    
    - UserExtention: 用户扩展字段表 目前只有风力使用，使用到的字段用fl前缀来标识
    
   1. String userId  用户id   
   2. String flType  执行人的岗位或者身份，数据项：1-超级管理员，2-总部，3-分部（仓库）。注意本字段和order表对应user的type数据概念不同
     
     
    - Team: 团队表
    
   1. String teamId     团队Id uuid
   2. String name       团队名字
   3. String avatar     团队头像
   4. int  level        团队等级  跟团队默认是0 往下递增
   5. String rootId     当前团队所属根团队(level = 0)的团队
   6. Date createTime   创建时间
   7. Date updateTime   更新时间
   8. String parentIds    父团队id
   9. String remark 团队备注
   10. long TeamIdNo 该团队对应redis的key值
   11. project 城市名 给风力用
   
    - TeamExtention: 团队扩展字段表
    
   1. String teamId   团队Id
   2. String flAdress 团队地址
   3. String flLinker 联系人
   4. String flTel    联系人电话
   5. BigDecimal flCost 每单价格

    - Role： 角色表 注：目前只存在人的标签和团队的标签，两种不用区分类型，可以混用
   
   1. String roleId     角色Id
   2. String name    标签名 通过这个关键词来判断是否具有某些权限
   3. String roleTypeId 标签所属类别id
   4. Date createTime   角色创建时间
   5.Date updateTime   角色更新时间
       
       
        
   
    - TeamRoleRelation： 团队角色关系表(目前有两种情况需要给团队添加角色
                          1.需要在新用户注册时维护  注册时根据传入的字段给用户创建一个团队，并且团队打上该字段的角色)
                          2.风力的仓会在团队上打上角色标签
        
   1. long id   id
   2. String teamId  团队Id
   3. String roleId  角色Id      
      
     - UserTeamRoleRelation： 用户角色关系表(一对多)  用户在分组中的标签
             
   1. long id   id
   2. String userId  用户Id
   3. String teamId  团队Id
   3. String roleId  角色Id 
   
   
     - UserTeamRelation：用户与团队关系，mananger为true代表是管理员 false代表为普通成员
   
   1. long id;
   2. String userId  用户Id
   3. String teamId  团队Id
   4. boolean mananger  是否是管理员
 
   
    - Account: 资金账号表
   
   1. String rootTeamId  根团队Id 创建根团队时指定
   2. BigDecimal amount  账户当前余额
   3. Date updateTime    资金变动时间
   
    - CapitalDetail: 账户明细表
    
   1. String id         唯一id
   2. String rootTeamId 根团队Id
   3. String teamId     消费的分组Id
   4. String userId     消费用户Id
   5. BigDecimal cost   本次账单金额
   6. String remark     消费备注
   7. BigDecimal amount 本次消费后的余额
   8. Date createTime   订单生产时间
   9. int rechargeType  充值渠道 1.微信 2.支付宝 仅在充值订单时产生
   10. String payId     订单Id
   
