﻿# Ticket system

 ## Project Overview

The project involves modeling a simplified version of a Ticket system or team management with the following features:

1. **Employees:**
   - An IT company has employees.
   - Each employee has a role (CEO, PM, DEV).

2. **Teams:**
   - Every employee, except the CEO, is associated with a team.

3. **Time Tracking:**
   - Each employee has a badge used for office entry/exit and time recording.

4. **Project Management:**
   - The company works on projects assigned by the CEO to a PM.

5. **Task Management:**
   - The PM creates tasks for the project with a description, status, and deadline.
   - Tasks can be assigned to one or more developers (employees with the DEV role).
   - Tasks can have commits (messages or notes) made by a developer.

6. **Hiring:**
   - The CEO can hire PMs or DEVs.

## Interface Operations

Create an interface (Web or CLI) to perform the following operations:

1. **Assign Task:**
   - Assign a task to a developer.

2. **Remove Task:**
   - Remove a task from a developer.

3. **Show In-Progress Tasks:**
   - Show all "in progress" tasks of a developer.

4. **Cross-Team Projects:**
   - Display cross-team projects (a project is cross-team if it has developers from at least 2 different teams working on its tasks).

5. **Create DEV and Assign to Team:**
   - Create a new DEV and assign them to a team.

6. **Reference PM of a DEV:**
   - Show the reference PM of a DEV.

7. **Tasks Exceeding Deadline:**
   - Show tasks that have exceeded the deadline with the DEVs who worked on them and their respective commits.

## Keywords

- **ORM (Object-Relational Mapping)**
- **Database Migration**
- **Data Seeding**
- **Dependency Injection**
