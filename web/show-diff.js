// Retrieve the response from session storage
const data = JSON.parse(sessionStorage.getItem('compareResponse'));

// Handle the response data
const oldFileIframe = document.querySelector('#oldFileHltd');
oldFileIframe.src = oldFileIframe.src + data.oldFileBytes + '#view=fitH';
oldFileIframe.hidden = false;

const newFileIframe = document.querySelector('#newFileHltd');
newFileIframe.src = newFileIframe.src + data.newFileBytes + '#view=fitH';
newFileIframe.hidden = false;
