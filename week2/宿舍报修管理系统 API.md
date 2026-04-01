---
title: 宿舍报修管理系统 API
language_tabs:
  - shell: Shell
  - http: HTTP
  - javascript: JavaScript
  - ruby: Ruby
  - python: Python
  - php: PHP
  - java: Java
  - go: Go
toc_footers: []
includes: []
search: true
code_clipboard: true
highlight_theme: darkula
headingLevel: 2
generator: "@tarslib/widdershins v4.0.30"

---

# 宿舍报修管理系统 API

宿舍报修管理系统接口文档

Base URLs:

# Authentication

- HTTP Authentication, scheme: bearer<br/>登录后获取 token，在请求头中携带

# 公共接口

## POST 图片上传

POST /api/upload

上传图片文件，返回图片URL

> Body 请求参数

```yaml
file: ""

```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 是 |none|
|» file|body|string(binary)| 是 |图片文件|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "url": "string"
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|上传成功|[UploadResponse](#schemauploadresponse)|

# 学生端

## POST 学生登录

POST /api/student/login

学生使用学号和密码登录

> Body 请求参数

```json
{
  "studentNum": "string",
  "password": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[StudentLoginRequest](#schemastudentloginrequest)| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "token": "string",
    "id": 0,
    "studentNum": "string",
    "name": "string"
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|登录成功|[StudentLoginResponse](#schemastudentloginresponse)|

## POST 学生注册

POST /api/student/register

学生注册账号

> Body 请求参数

```json
{
  "studentNum": "string",
  "password": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[StudentRegisterRequest](#schemastudentregisterrequest)| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "string",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|注册成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|false|none||none|
|» message|string|false|none||none|
|» data|null|false|none||none|

## GET 获取学生信息

GET /api/student/info

获取当前登录学生的个人信息

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "id": 0,
    "studentNum": "string",
    "name": "string",
    "gender": 0,
    "phone": "string",
    "dormitoryId": 0
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|获取成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|false|none||none|
|» message|string|false|none||none|
|» data|[StudentInfo](#schemastudentinfo)|false|none||none|
|»» id|integer|false|none||none|
|»» studentNum|string|false|none||none|
|»» name|string|false|none||none|
|»» gender|integer|false|none||1-男，2-女|
|»» phone|string|false|none||none|
|»» dormitoryId|integer|false|none||none|

## PUT 更新学生信息

PUT /api/student/info

更新当前登录学生的个人信息

> Body 请求参数

```json
{
  "name": "string",
  "gender": 0,
  "phone": "string",
  "dormitoryId": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[UpdateStudentInfoRequest](#schemaupdatestudentinforequest)| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "string",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|保存成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|false|none||none|
|» message|string|false|none||none|
|» data|null|false|none||none|

# 报修管理

## POST 提交报修单

POST /api/repair/submit

学生提交新的报修申请

> Body 请求参数

```json
{
  "type": 1,
  "description": "string",
  "imageUrl": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[RepairSubmitRequest](#schemarepairsubmitrequest)| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "id": 0,
    "orderNum": "string"
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|提交成功|[RepairSubmitResponse](#schemarepairsubmitresponse)|

## GET 获取我的报修列表

GET /api/repair/my-list

获取当前学生提交的所有报修单

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "string",
  "data": [
    {
      "id": 0,
      "orderNum": "string",
      "type": 0,
      "description": "string",
      "imageUrl": "string",
      "status": 0,
      "result": "string",
      "createTime": "2019-08-24T14:15:22Z",
      "adminId": 0,
      "adminName": "string"
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|获取成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|false|none||none|
|» message|string|false|none||none|
|» data|[[RepairItem](#schemarepairitem)]|false|none||none|
|»» id|integer|false|none||none|
|»» orderNum|string|false|none||none|
|»» type|integer|false|none||1-水电，2-家具，3-电器，4-网络，5-其他|
|»» description|string|false|none||none|
|»» imageUrl|string|false|none||none|
|»» status|integer|false|none||0-待分配，1-处理中，2-已完成，3-已取消|
|»» result|string¦null|false|none||none|
|»» createTime|string(date-time)|false|none||none|
|»» adminId|integer¦null|false|none||none|
|»» adminName|string¦null|false|none||none|

## PUT 取消报修

PUT /api/repair/{id}/cancel

取消待分配的报修单

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |报修单ID|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "string",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|取消成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|false|none||none|
|» message|string|false|none||none|
|» data|null|false|none||none|

# 管理员端

## POST 管理员登录

POST /api/admin/login

管理员使用账号和密码登录

> Body 请求参数

```json
{
  "adminNum": "string",
  "password": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[AdminLoginRequest](#schemaadminloginrequest)| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "token": "string",
    "id": 0,
    "adminNum": "string",
    "name": "string"
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|登录成功|[AdminLoginResponse](#schemaadminloginresponse)|

## POST 管理员注册

POST /api/admin/register

管理员注册账号

> Body 请求参数

```json
{
  "adminNum": "string",
  "password": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[AdminRegisterRequest](#schemaadminregisterrequest)| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "string",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|注册成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|false|none||none|
|» message|string|false|none||none|
|» data|null|false|none||none|

## GET 获取管理员信息

GET /api/admin/info

获取当前登录管理员的个人信息

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "id": 0,
    "adminNum": "string",
    "name": "string",
    "phone": "string"
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|获取成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|false|none||none|
|» message|string|false|none||none|
|» data|[AdminInfo](#schemaadmininfo)|false|none||none|
|»» id|integer|false|none||none|
|»» adminNum|string|false|none||none|
|»» name|string|false|none||none|
|»» phone|string|false|none||none|

## PUT 更新管理员信息

PUT /api/admin/info

更新当前登录管理员的个人信息

> Body 请求参数

```json
{
  "name": "string",
  "phone": "string"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|[UpdateAdminInfoRequest](#schemaupdateadmininforequest)| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "string",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|保存成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|false|none||none|
|» message|string|false|none||none|
|» data|null|false|none||none|

## GET 获取所有报修单

GET /api/admin/repairs

管理员获取所有报修单列表，可按状态筛选

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|status|query|integer| 否 |0-待分配，1-处理中，2-已完成，3-已取消|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "string",
  "data": [
    {
      "id": 0,
      "orderNum": "string",
      "studentId": 0,
      "studentName": "string",
      "studentPhone": "string",
      "dormitoryLocation": "string",
      "type": 0,
      "description": "string",
      "imageUrl": "string",
      "status": 0,
      "result": "string",
      "createTime": "2019-08-24T14:15:22Z",
      "adminId": 0,
      "adminName": "string"
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|获取成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|false|none||none|
|» message|string|false|none||none|
|» data|[[AdminRepairItem](#schemaadminrepairitem)]|false|none||none|
|»» id|integer|false|none||none|
|»» orderNum|string|false|none||none|
|»» studentId|integer|false|none||none|
|»» studentName|string|false|none||none|
|»» studentPhone|string|false|none||none|
|»» dormitoryLocation|string|false|none||none|
|»» type|integer|false|none||none|
|»» description|string|false|none||none|
|»» imageUrl|string|false|none||none|
|»» status|integer|false|none||none|
|»» result|string¦null|false|none||none|
|»» createTime|string(date-time)|false|none||none|
|»» adminId|integer¦null|false|none||none|
|»» adminName|string¦null|false|none||none|

## GET 获取报修单详情

GET /api/admin/repair/{id}

管理员获取指定报修单的详细信息

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |报修单ID|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "id": 0,
    "orderNum": "string",
    "studentId": 0,
    "studentName": "string",
    "studentPhone": "string",
    "dormitoryLocation": "string",
    "type": 0,
    "description": "string",
    "imageUrl": "string",
    "status": 0,
    "result": "string",
    "createTime": "2019-08-24T14:15:22Z",
    "adminId": 0,
    "adminName": "string"
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|获取成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|false|none||none|
|» message|string|false|none||none|
|» data|[AdminRepairItem](#schemaadminrepairitem)|false|none||none|
|»» id|integer|false|none||none|
|»» orderNum|string|false|none||none|
|»» studentId|integer|false|none||none|
|»» studentName|string|false|none||none|
|»» studentPhone|string|false|none||none|
|»» dormitoryLocation|string|false|none||none|
|»» type|integer|false|none||none|
|»» description|string|false|none||none|
|»» imageUrl|string|false|none||none|
|»» status|integer|false|none||none|
|»» result|string¦null|false|none||none|
|»» createTime|string(date-time)|false|none||none|
|»» adminId|integer¦null|false|none||none|
|»» adminName|string¦null|false|none||none|

## DELETE 删除报修单

DELETE /api/admin/repair/{id}

管理员删除报修单

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |报修单ID|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "string",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|删除成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|false|none||none|
|» message|string|false|none||none|
|» data|null|false|none||none|

## PUT 更新报修状态

PUT /api/admin/repair/{id}/status

管理员更新报修单的状态和处理结果

> Body 请求参数

```json
{
  "status": 1,
  "result": "string",
  "adminId": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|id|path|integer| 是 |报修单ID|
|body|body|[UpdateRepairStatusRequest](#schemaupdaterepairstatusrequest)| 是 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "string",
  "data": null
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|操作成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|false|none||none|
|» message|string|false|none||none|
|» data|null|false|none||none|

# 宿舍管理

## GET 获取宿舍列表

GET /api/dormitory/list

获取所有宿舍列表

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "message": "string",
  "data": [
    {
      "id": 0,
      "building": "string",
      "room": "string"
    }
  ]
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|获取成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» code|integer|false|none||none|
|» message|string|false|none||none|
|» data|[[DormitoryItem](#schemadormitoryitem)]|false|none||none|
|»» id|integer|false|none||none|
|»» building|string|false|none||none|
|»» room|string|false|none||none|

# 数据模型

<h2 id="tocS_Response">Response</h2>

<a id="schemaresponse"></a>
<a id="schema_Response"></a>
<a id="tocSresponse"></a>
<a id="tocsresponse"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": {}
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||状态码：200成功，401未授权|
|message|string|false|none||提示信息|
|data|object|false|none||返回数据|

<h2 id="tocS_StudentLoginRequest">StudentLoginRequest</h2>

<a id="schemastudentloginrequest"></a>
<a id="schema_StudentLoginRequest"></a>
<a id="tocSstudentloginrequest"></a>
<a id="tocsstudentloginrequest"></a>

```json
{
  "studentNum": "string",
  "password": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|studentNum|string|true|none||学号|
|password|string|true|none||密码|

<h2 id="tocS_StudentLoginResponse">StudentLoginResponse</h2>

<a id="schemastudentloginresponse"></a>
<a id="schema_StudentLoginResponse"></a>
<a id="tocSstudentloginresponse"></a>
<a id="tocsstudentloginresponse"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "token": "string",
    "id": 0,
    "studentNum": "string",
    "name": "string"
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|object|false|none||none|
|» token|string|false|none||none|
|» id|integer|false|none||none|
|» studentNum|string|false|none||none|
|» name|string|false|none||none|

<h2 id="tocS_StudentRegisterRequest">StudentRegisterRequest</h2>

<a id="schemastudentregisterrequest"></a>
<a id="schema_StudentRegisterRequest"></a>
<a id="tocSstudentregisterrequest"></a>
<a id="tocsstudentregisterrequest"></a>

```json
{
  "studentNum": "string",
  "password": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|studentNum|string|true|none||学号|
|password|string|true|none||密码|

<h2 id="tocS_StudentInfo">StudentInfo</h2>

<a id="schemastudentinfo"></a>
<a id="schema_StudentInfo"></a>
<a id="tocSstudentinfo"></a>
<a id="tocsstudentinfo"></a>

```json
{
  "id": 0,
  "studentNum": "string",
  "name": "string",
  "gender": 0,
  "phone": "string",
  "dormitoryId": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer|false|none||none|
|studentNum|string|false|none||none|
|name|string|false|none||none|
|gender|integer|false|none||1-男，2-女|
|phone|string|false|none||none|
|dormitoryId|integer|false|none||none|

<h2 id="tocS_UpdateStudentInfoRequest">UpdateStudentInfoRequest</h2>

<a id="schemaupdatestudentinforequest"></a>
<a id="schema_UpdateStudentInfoRequest"></a>
<a id="tocSupdatestudentinforequest"></a>
<a id="tocsupdatestudentinforequest"></a>

```json
{
  "name": "string",
  "gender": 0,
  "phone": "string",
  "dormitoryId": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|name|string|false|none||none|
|gender|integer|false|none||1-男，2-女|
|phone|string|false|none||none|
|dormitoryId|integer|false|none||none|

<h2 id="tocS_RepairSubmitRequest">RepairSubmitRequest</h2>

<a id="schemarepairsubmitrequest"></a>
<a id="schema_RepairSubmitRequest"></a>
<a id="tocSrepairsubmitrequest"></a>
<a id="tocsrepairsubmitrequest"></a>

```json
{
  "type": 1,
  "description": "string",
  "imageUrl": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|type|integer|true|none||1-水电，2-家具，3-电器，4-网络，5-其他|
|description|string|true|none||故障描述|
|imageUrl|string|false|none||图片URL|

#### 枚举值

|属性|值|
|---|---|
|type|1|
|type|2|
|type|3|
|type|4|
|type|5|

<h2 id="tocS_RepairSubmitResponse">RepairSubmitResponse</h2>

<a id="schemarepairsubmitresponse"></a>
<a id="schema_RepairSubmitResponse"></a>
<a id="tocSrepairsubmitresponse"></a>
<a id="tocsrepairsubmitresponse"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "id": 0,
    "orderNum": "string"
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|object|false|none||none|
|» id|integer|false|none||none|
|» orderNum|string|false|none||none|

<h2 id="tocS_RepairItem">RepairItem</h2>

<a id="schemarepairitem"></a>
<a id="schema_RepairItem"></a>
<a id="tocSrepairitem"></a>
<a id="tocsrepairitem"></a>

```json
{
  "id": 0,
  "orderNum": "string",
  "type": 0,
  "description": "string",
  "imageUrl": "string",
  "status": 0,
  "result": "string",
  "createTime": "2019-08-24T14:15:22Z",
  "adminId": 0,
  "adminName": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer|false|none||none|
|orderNum|string|false|none||none|
|type|integer|false|none||1-水电，2-家具，3-电器，4-网络，5-其他|
|description|string|false|none||none|
|imageUrl|string|false|none||none|
|status|integer|false|none||0-待分配，1-处理中，2-已完成，3-已取消|
|result|string¦null|false|none||none|
|createTime|string(date-time)|false|none||none|
|adminId|integer¦null|false|none||none|
|adminName|string¦null|false|none||none|

<h2 id="tocS_AdminRepairItem">AdminRepairItem</h2>

<a id="schemaadminrepairitem"></a>
<a id="schema_AdminRepairItem"></a>
<a id="tocSadminrepairitem"></a>
<a id="tocsadminrepairitem"></a>

```json
{
  "id": 0,
  "orderNum": "string",
  "studentId": 0,
  "studentName": "string",
  "studentPhone": "string",
  "dormitoryLocation": "string",
  "type": 0,
  "description": "string",
  "imageUrl": "string",
  "status": 0,
  "result": "string",
  "createTime": "2019-08-24T14:15:22Z",
  "adminId": 0,
  "adminName": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer|false|none||none|
|orderNum|string|false|none||none|
|studentId|integer|false|none||none|
|studentName|string|false|none||none|
|studentPhone|string|false|none||none|
|dormitoryLocation|string|false|none||none|
|type|integer|false|none||none|
|description|string|false|none||none|
|imageUrl|string|false|none||none|
|status|integer|false|none||none|
|result|string¦null|false|none||none|
|createTime|string(date-time)|false|none||none|
|adminId|integer¦null|false|none||none|
|adminName|string¦null|false|none||none|

<h2 id="tocS_AdminLoginRequest">AdminLoginRequest</h2>

<a id="schemaadminloginrequest"></a>
<a id="schema_AdminLoginRequest"></a>
<a id="tocSadminloginrequest"></a>
<a id="tocsadminloginrequest"></a>

```json
{
  "adminNum": "string",
  "password": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|adminNum|string|true|none||管理员账号|
|password|string|true|none||密码|

<h2 id="tocS_AdminLoginResponse">AdminLoginResponse</h2>

<a id="schemaadminloginresponse"></a>
<a id="schema_AdminLoginResponse"></a>
<a id="tocSadminloginresponse"></a>
<a id="tocsadminloginresponse"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "token": "string",
    "id": 0,
    "adminNum": "string",
    "name": "string"
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|object|false|none||none|
|» token|string|false|none||none|
|» id|integer|false|none||none|
|» adminNum|string|false|none||none|
|» name|string|false|none||none|

<h2 id="tocS_AdminRegisterRequest">AdminRegisterRequest</h2>

<a id="schemaadminregisterrequest"></a>
<a id="schema_AdminRegisterRequest"></a>
<a id="tocSadminregisterrequest"></a>
<a id="tocsadminregisterrequest"></a>

```json
{
  "adminNum": "string",
  "password": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|adminNum|string|true|none||管理员账号|
|password|string|true|none||密码|

<h2 id="tocS_AdminInfo">AdminInfo</h2>

<a id="schemaadmininfo"></a>
<a id="schema_AdminInfo"></a>
<a id="tocSadmininfo"></a>
<a id="tocsadmininfo"></a>

```json
{
  "id": 0,
  "adminNum": "string",
  "name": "string",
  "phone": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer|false|none||none|
|adminNum|string|false|none||none|
|name|string|false|none||none|
|phone|string|false|none||none|

<h2 id="tocS_UpdateAdminInfoRequest">UpdateAdminInfoRequest</h2>

<a id="schemaupdateadmininforequest"></a>
<a id="schema_UpdateAdminInfoRequest"></a>
<a id="tocSupdateadmininforequest"></a>
<a id="tocsupdateadmininforequest"></a>

```json
{
  "name": "string",
  "phone": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|name|string|false|none||none|
|phone|string|false|none||none|

<h2 id="tocS_UpdateRepairStatusRequest">UpdateRepairStatusRequest</h2>

<a id="schemaupdaterepairstatusrequest"></a>
<a id="schema_UpdateRepairStatusRequest"></a>
<a id="tocSupdaterepairstatusrequest"></a>
<a id="tocsupdaterepairstatusrequest"></a>

```json
{
  "status": 1,
  "result": "string",
  "adminId": 0
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|status|integer|true|none||1-处理中，2-已完成|
|result|string|false|none||处理结果|
|adminId|integer|false|none||管理员ID|

#### 枚举值

|属性|值|
|---|---|
|status|1|
|status|2|

<h2 id="tocS_DormitoryItem">DormitoryItem</h2>

<a id="schemadormitoryitem"></a>
<a id="schema_DormitoryItem"></a>
<a id="tocSdormitoryitem"></a>
<a id="tocsdormitoryitem"></a>

```json
{
  "id": 0,
  "building": "string",
  "room": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer|false|none||none|
|building|string|false|none||none|
|room|string|false|none||none|

<h2 id="tocS_UploadResponse">UploadResponse</h2>

<a id="schemauploadresponse"></a>
<a id="schema_UploadResponse"></a>
<a id="tocSuploadresponse"></a>
<a id="tocsuploadresponse"></a>

```json
{
  "code": 0,
  "message": "string",
  "data": {
    "url": "string"
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||none|
|message|string|false|none||none|
|data|object|false|none||none|
|» url|string|false|none||none|

