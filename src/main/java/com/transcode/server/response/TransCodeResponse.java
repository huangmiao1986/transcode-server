package com.transcode.server.response;

import java.util.List;

public class TransCodeResponse {
	private String ret;
	private String errinfo;
	private List<TransCodeNode> list;
	
	public TransCodeResponse(String ret,String errinfo) {
		super();
		this.ret = ret;
		this.errinfo = errinfo;
	}
	
	public TransCodeResponse(String ret,String errinfo,List<TransCodeNode> list ) {
		super();
		this.ret = ret;
		this.errinfo = errinfo;
		this.list = list;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getErrinfo() {
		return errinfo;
	}

	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}

	public List<TransCodeNode> getList() {
		return list;
	}

	public void setList(List<TransCodeNode> list) {
		this.list = list;
	}
	
}
