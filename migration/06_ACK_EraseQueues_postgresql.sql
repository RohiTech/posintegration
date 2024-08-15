-- Sep 18, 2011 7:21:57 PM MYT
-- set ACK to erase Queue
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsCentrallyMaintained,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,2153,0,53271,53567,20,'ACK',TO_TIMESTAMP('2011-09-18 19:21:55','YYYY-MM-DD HH24:MI:SS'),100,'N','Erase last messages from Product/Customer queues','U',1,'Keep both Products and Customer Queue Paths blank for this to work','Y','Y','N','N','Erase Last Queues',70,TO_TIMESTAMP('2011-09-18 19:21:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 18, 2011 7:21:57 PM MYT
-- set ACK to erase Queue
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=53567 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Sep 18, 2011 7:22:08 PM MYT
-- set ACK to erase Queue
UPDATE AD_SysConfig SET Value='pass',Updated=TO_TIMESTAMP('2011-09-18 19:22:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=50002
;

-- Sep 18, 2011 7:22:12 PM MYT
-- set ACK to erase Queue
UPDATE AD_SysConfig SET Value='remarks',Updated=TO_TIMESTAMP('2011-09-18 19:22:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=50003
;

