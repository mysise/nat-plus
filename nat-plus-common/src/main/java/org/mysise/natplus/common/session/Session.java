package org.mysise.natplus.common.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author memory
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    /**
     *  用户唯一性标识
     */
    private String token;

}
