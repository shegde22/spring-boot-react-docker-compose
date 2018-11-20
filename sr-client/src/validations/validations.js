import axios from 'axios';
import uri from '../config/uri';
// Helper
export async function courseExists(dept, cnum) {
  try {
    const response = await axios.get(
      `${uri}/courses/details?dept=${dept}&cnum=${cnum}`
    );
    if (response.data.errors && response.data.errors.length > 0) return false;
    return response.data.course !== null;
  } catch (err) {
    console.log(err);
    return false;
  }
}

export async function preExists(pre) {
  try {
    const response = await axios.get(
      `${uri}/prerequisites/info?dept=${pre.id.dept}&cnum=${
        pre.id.cnum
      }&preDept=${pre.id.preDept}&preCnum=${pre.id.preCnum}`
    );
    console.log(response);
    if (response.data.errors && response.data.errors.length > 0) return false;
    return response.data.prerequisite && response.data.prerequisite !== null;
  } catch (err) {
    console.log(err);
    return false;
  }
}

export async function studentExists(bnum) {
  try {
    const response = await axios.get(`${uri}/students/${bnum}`);
    console.log(response);
    if (response.data.errors && response.data.errors.length > 0) return false;
    return response.data.student && response.data.student !== null;
  } catch (err) {
    console.log(err);
    return false;
  }
}

export async function studentExistsByEmail(email) {
  try {
    const response = await axios.post(`${uri}/students/email`, { email });
    console.log(response);
    if (response.data.errors && response.data.errors.length > 0) return false;
    return response.data.student && response.data.student !== null;
  } catch (err) {
    console.log(err);
    return false;
  }
}
