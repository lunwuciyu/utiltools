/**
 * @ProjectName: 公安部出入境综合数据应用视频监控系统项目
 * @Copyright: 2015 中华人民共和国公安部 All Rights Reserved .
 * @address: http://www.mps.gov.cn
 * @date: 2015-7-10 下午7:38:37
 * @Description: 本内容仅限于中华人民共和国公安部内部使用，禁止转发.
 */
package exception;

/**
 * <p></p>
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015-7-10
 * @modify by reason:{方法名}:{原因}
 */
public class ParamsTypeErrorException extends RuntimeException{
	
	private static final long serialVersionUID = 7298453228772589863L;

	public ParamsTypeErrorException() {
		super();
	}

	public ParamsTypeErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParamsTypeErrorException(String message) {
		super(message);
	}

	public ParamsTypeErrorException(Throwable cause) {
		super(cause);
	}
}
