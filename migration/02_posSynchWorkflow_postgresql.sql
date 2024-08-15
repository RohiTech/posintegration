-- Sep 4, 2011 7:22:20 PM MYT
-- Whole POS Synch cycle in single Workflow
INSERT INTO AD_WF_Node (AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,"action",Cost,Created,CreatedBy,Description,DocAction,Duration,DynPriorityChange,EntityType,Help,IsActive,IsCentrallyMaintained,JoinElement,"limit",Name,Priority,SplitElement,Updated,UpdatedBy,Value,WaitTime,WaitingTime,WorkingTime,XPosition,YPosition) VALUES (0,0,50124,50025,'Z',0,TO_TIMESTAMP('2011-09-04 19:22:19','YYYY-MM-DD HH24:MI:SS'),100,'Process Imported Orders from external POS exported via ActiveMQ','CO',0,0,'A','This is a normal Import Order window to process the imported orders from external POS after its been taken from the queue','Y','Y','X',0,'Process Imported Orders',0,'X',TO_TIMESTAMP('2011-09-04 19:22:19','YYYY-MM-DD HH24:MI:SS'),100,'Process Imported Orders',0,0,0,0,0)
;

-- Sep 4, 2011 7:22:20 PM MYT
-- Whole POS Synch cycle in single Workflow
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_WF_Node_ID=50124 AND NOT EXISTS (SELECT * FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- Sep 4, 2011 7:23:17 PM MYT
-- Whole POS Synch cycle in single Workflow
UPDATE AD_WF_Node SET AD_Window_ID=281, "action"='W',Updated=TO_TIMESTAMP('2011-09-04 19:23:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=50124
;

-- Sep 4, 2011 7:24:09 PM MYT
-- Whole POS Synch cycle in single Workflow
UPDATE AD_WF_NodeNext SET AD_WF_Next_ID=50124,Updated=TO_TIMESTAMP('2011-09-04 19:24:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_NodeNext_ID=50093
;

-- Sep 4, 2011 7:24:21 PM MYT
-- Whole POS Synch cycle in single Workflow
INSERT INTO AD_WF_NodeNext (AD_Client_ID,AD_Org_ID,AD_WF_Next_ID,AD_WF_NodeNext_ID,AD_WF_Node_ID,Created,CreatedBy,EntityType,IsActive,IsStdUserWorkflow,SeqNo,Updated,UpdatedBy) VALUES (0,0,50121,50094,50124,TO_TIMESTAMP('2011-09-04 19:24:20','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N',10,TO_TIMESTAMP('2011-09-04 19:24:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 7:40:21 PM MYT
-- Whole POS Synch cycle in single Workflow
UPDATE AD_Task SET OS_Command='/Applications/apache-activemq-5.5.0/bin/macosx/activemq start',Updated=TO_TIMESTAMP('2011-09-04 19:40:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Task_ID=50000
;

-- Sep 4, 2011 7:40:39 PM MYT
-- Whole POS Synch cycle in single Workflow
UPDATE AD_Task SET OS_Command='/Applications/apache-activemq-5.5.0/bin/macosx/activemq stop',Updated=TO_TIMESTAMP('2011-09-04 19:40:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Task_ID=50001
;

