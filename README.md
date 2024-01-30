# backend_workflo

WorkFlo

user

POST
createUser
https://backendworkflo-production.up.railway.app/api/v1/register
{
"firstName":"firstName",
"lastName":"lastName",
"email":"tfq83x+16bdfz8jymh0@sharklasers.com",
"password":"Password12@",
"phoneNumber":"09063587907"
}

GET
confirmMail
https://backendworkflo-production.up.railway.app/api/v1/user/confirm?email=tfq83x+16bdfz8jymh0@sharklasers.com&token=3e47a99c-4520-4284-995c-301c07865fbc
Query Params
email tfq83x+16bdfz8jymh0@sharklasers.com
token 3e47a99c-4520-4284-995c-301c07865fbc

POST
createProfile
https://backendworkflo-production.up.railway.app/api/v1/user/profile
Query Params
Body
form-data
image
id
portfolio {","}
about
jobTitle
skills [""]

GET
get user by Id
https://backendworkflo-production.up.railway.app/api/v1/user/1

POST
LoginUser
localhost:8080/api/v1/user/address
{
"userId": 1,
"city": "        ",
"country": " ",
"state": null,
"zipCode": "1234"
}

POST
create address
https://backendworkflo-production.up.railway.app/api/v1/user/address
{
"userId":1,
"city":"",
"country":"",
"state":"",
"zipCode":""
}

PATCH
Update Profile
https://backendworkflo-production.up.railway.app/api/v1/user/1/updateProfile
{
"firstName":"",
"lastName":"",
"phoneNumber":"",
"about":"",
"jobTitle":"",
"skills":[""],
"portFolio":{"": ""},
"country":"",
"state":"",
"city":"",
"zipCode": 234
}

POST
Upload ProfilePicture
https://backendworkflo-production.up.railway.app/api/v1/user/1/profilePicture
Body
form-data
image

project
GET
get All Joined Projects
https://backendworkflo-production.up.railway.app/api/v1/user/project/1/joinedProjects

POST
CreateProject
https://backendworkflo-production.up.railway.app/api/v1/user/project
Body
form-data
image
userId number
project_name goodd
summary ART
description good
category
skills []

DELETE
Delete Project
https://backendworkflo-production.up.railway.app/api/v1/user/project/1/1

GET
View User Created Project
https://backendworkflo-production.up.railway.app/api/v1/user/project/1/getAllProjects

PATCH
Remove Project Member
https://backendworkflo-production.up.railway.app/api/v1/user/project/1/1/1

POST
CreateProject Copy
https://backendworkflo-production.up.railway.app/api/v1/user/project
Body
form-data
userId 2
project_name new project
summary goodd
category ART

GET
Get All Project

https://backendworkflo-production.up.railway.app/api/v1/user/project/1/10

GET
Get Project By Id
https://backendworkflo-production.up.railway.app/api/v1/user/project?id=1

Query Params
id 1

vacancy

POST
create vacancy
https://backendworkflo-production.up.railway.app/api/v1/user/project/vacancy

{
"projectIdentifier": 1,
"userIdentifier": 1,
"neededSkills": [""],
"text": ""
}

PATCH
Update Vacancy

https://backendworkflo-production.up.railway.app/api/v1/user/project/vacancy?userId=1&id=1
Query Params
userId 1
id 1
{
"text":"",
"neededSkills":[""],
"status":""
}

GET
getVacancyRequest
https://backendworkflo-production.up.railway.app/api/v1/user/project/vacancy
{
"userId":1,
"vacancyId":2,
"status":""
}

bid request

POST
Bid For Project
https://backendworkflo-production.up.railway.app/api/v1/user/project/bid
{
"userId":1,
"vacancyId":2,
"message":""
}

PATCH
Decide Bid Request
https://backendworkflo-production.up.railway.app/api/v1user/project/bid
{
"status":"",
"userId":1,
"requestId": 2
}

GET
Check User Request

https://backendworkflo-production.up.railway.app/api/v1/user/project/bid/1‣潷歲汦੯