package github.jk1.smtpidea.log

import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.DefaultMutableTreeNode
import java.util.Enumeration

/**
 *  Logs all mail protocol commands and responses, grouped by corresponding protocol sessions.
 *  It is safe to operate this instance from any arbitrary thread.
 */
public object SmtpLog {

    public fun logRequest(sessionId: String, content: String?) {

    }

    public fun logResponse(sessionId: String, content: String?) {

    }
}