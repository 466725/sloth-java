/****** Script for SelectTopNRows command from SSMS  ******/
SELECT TOP (1000) [IT_Session_ID]
      ,[VISTA_Session_ID]
      ,[Film_ID]
      ,[Film_Enhancement_Code]
      ,[Film_Name]
      ,[Film_Enhancement_Description_English]
      ,[Film_Enhancement_Description_French]
      ,[Title_ID]
      ,[Title_Name]
      ,[Location_ID]
      ,[Location_Name_English]
      ,[Province_Code]
      ,[Screen_Number]
      ,[Screen_Name]
      ,[Show_Start_DateTime]
      ,[Show_End_DateTime]
      ,[Business_Date]
      ,[Seats_Available]
      ,[Seats_Sold]
      ,[Seats_Held]
      ,[Seats_House]
      ,[Is_Reserved_Seating]
      ,[Is_Sold_Out]
      ,[Film_Rating]
      ,[Is_Pass_Restriction]
      ,[Warnings_English]
      ,[Warnings_French]
      ,[Last_Updated]
      ,[VISTA_Screen_Layout_ID]
      ,[Session_dtmLastUpdate]
      ,[Session_DateTime_Last_Updated]
      ,[Is_Showtime_Enabled]
  FROM [CINEPLEX_com].[dbo].[FC_Sessions]
  WHERE Film_ID = 28160
  ORDER BY Show_Start_DateTime


SELECT *
FROM [CINEPLEX_com].[dbo].[FC_Sessions] S
INNER JOIN FC_Session_Attributes SA ON S.IT_Session_ID = SA.IT_Session_ID
INNER JOIN FC_Attribute A ON SA.FC_Attribute_ID = A.FC_Attribute_ID
WHERE Film_ID = 28160 AND A.Is_3D = 1
ORDER BY Show_Start_DateTime