# Unlenen OpenStack Java API & Swagger Rest Server

## Written By
- Nebi Volkan ÜNLENEN (unlenen@gmail.com)
- Onurcan PEKGÖZ (onurcanaylin@gmail.com)

## Introduction

This project provides java api and OpenAPI supported Rest Controller to make openstack easier to use.

In normal world OpenStack APIs are well documentated but no easy way to create client applications. This project may help you to create swagger based client application according to your programming languages.

## Swagger URL

- http://your-ip:8080/swagger-ui/index.html

## Supported Modules

### Identity 
- login
- Get Catalogs
- Domains
  - Get
  - Create
  - Delete
- Projects
  - Get
  - Create
  - Delete
- User
  - Get
  - Create
  - Delete
- Role
  - Get
  - Assign Role To Domain
  - Assing Role To Project
### Compute
- Flavor
  - Get
  - Create
  - Delete
- Keypair
  - Get
  - Create
  - Delete
- Quota
  - Get
  - Update
- Servers
  - Get
  - Create
  - Delete
- Floating IP
  - Assosiate
  - Disassociate
### Image
- Image
  - Get
  - Create
  - Delete
  - Upload Image File
  - Download Image File
- Tag
  - Add
  - Delete
### Orchestration
- Stack
  - Get
  - Create
  - Delete
### Network
- Network
  - Get
  - Create
  - Delete
- SubNetwork
  - Get
  - Create
  - Delete
- Router
  - Get
  - Create
  - Delete
  - Add Interface
- Floating IP
  - Get 
  - Create
  - Delete
- Security Group
  - Get
  - Create
  - Delete
- Security Group Rules
  - Add
  - Delete
  - Get
### Volume
- Get
- Create
- Delete
  
