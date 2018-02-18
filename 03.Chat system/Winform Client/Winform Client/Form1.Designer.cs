namespace Winform_Client
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.buttonSend = new System.Windows.Forms.Button();
            this.textBox_Input = new System.Windows.Forms.TextBox();
            this.textBox_Output = new System.Windows.Forms.TextBox();
            this.textBox_ClientName = new System.Windows.Forms.TextBox();
            this.listBox_ClientList = new System.Windows.Forms.ListBox();
            this.SuspendLayout();
            // 
            // buttonSend
            // 
            this.buttonSend.Location = new System.Drawing.Point(569, 393);
            this.buttonSend.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.buttonSend.Name = "buttonSend";
            this.buttonSend.Size = new System.Drawing.Size(100, 28);
            this.buttonSend.TabIndex = 0;
            this.buttonSend.Text = "Send";
            this.buttonSend.UseVisualStyleBackColor = true;
            this.buttonSend.Click += new System.EventHandler(this.buttonSend_Click);
            // 
            // textBox_Input
            // 
            this.textBox_Input.Location = new System.Drawing.Point(43, 393);
            this.textBox_Input.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.textBox_Input.Name = "textBox_Input";
            this.textBox_Input.Size = new System.Drawing.Size(511, 22);
            this.textBox_Input.TabIndex = 2;
            // 
            // textBox_Output
            // 
            this.textBox_Output.Location = new System.Drawing.Point(43, 64);
            this.textBox_Output.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.textBox_Output.Multiline = true;
            this.textBox_Output.Name = "textBox_Output";
            this.textBox_Output.Size = new System.Drawing.Size(511, 288);
            this.textBox_Output.TabIndex = 3;
            // 
            // textBox_ClientName
            // 
            this.textBox_ClientName.Location = new System.Drawing.Point(55, 20);
            this.textBox_ClientName.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.textBox_ClientName.Name = "textBox_ClientName";
            this.textBox_ClientName.ReadOnly = true;
            this.textBox_ClientName.Size = new System.Drawing.Size(132, 22);
            this.textBox_ClientName.TabIndex = 4;
            // 
            // listBox_ClientList
            // 
            this.listBox_ClientList.FormattingEnabled = true;
            this.listBox_ClientList.ItemHeight = 16;
            this.listBox_ClientList.Location = new System.Drawing.Point(573, 70);
            this.listBox_ClientList.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.listBox_ClientList.Name = "listBox_ClientList";
            this.listBox_ClientList.Size = new System.Drawing.Size(93, 276);
            this.listBox_ClientList.TabIndex = 5;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(685, 447);
            this.Controls.Add(this.listBox_ClientList);
            this.Controls.Add(this.textBox_ClientName);
            this.Controls.Add(this.textBox_Output);
            this.Controls.Add(this.textBox_Input);
            this.Controls.Add(this.buttonSend);
            this.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button buttonSend;
        private System.Windows.Forms.TextBox textBox_Input;
        private System.Windows.Forms.TextBox textBox_Output;
        private System.Windows.Forms.TextBox textBox_ClientName;
        private System.Windows.Forms.ListBox listBox_ClientList;
    }
}

